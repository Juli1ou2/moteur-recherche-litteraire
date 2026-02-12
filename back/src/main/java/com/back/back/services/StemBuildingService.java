package com.back.back.services;

import com.back.back.entities.Stem;
import com.back.back.repositories.BookRepository;
import com.back.back.repositories.IndexInverseRepository;
import com.back.back.repositories.StemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class StemBuildingService {

    private final TextProcessingService textProcessing;
    private final TextFilesReaderService textFilesReader;
    private final StemRepository stemRepository;
    private final BookRepository bookRepository;
    private final IndexInverseRepository indexInverseRepository;

    public void buildStems(Path textFilesPath) throws IOException {
        Map<String, Integer> documentFrequency = new ConcurrentHashMap<>();
//        Map<String, Stem> stemCache = new ConcurrentHashMap<>();
//        AtomicInteger counter = new AtomicInteger(1);

        List<Path> files = textFilesReader.listTextFiles(textFilesPath);
        System.out.println("Nombre de fichiers : " + files.size());

        ExecutorService executor = Executors.newFixedThreadPool(4);

        for (Path file : files) {
            executor.submit(() -> {
                try {
                    Map<String, Integer> termFrequencies = new HashMap<>();

                    try (BufferedReader reader = createSafeReader(file)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            for (String stem : textProcessing.extractStems(line)) {
                                termFrequencies.merge(stem, 1, Integer::sum);
                            }
                        }
                    }

                    for (String stem : termFrequencies.keySet()) {
                        documentFrequency.merge(stem, 1, Integer::sum);
                    }
                } catch (Exception e) {
                    System.err.println("Erreur fichier " + file.getFileName());
                    e.printStackTrace();
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        List<Stem> topStems = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : documentFrequency.entrySet()) {
            String stemValue = entry.getKey();
            int freq = entry.getValue();
            stemRepository.findByStem(stemValue)
                    .ifPresentOrElse(existing -> {
                        existing.setFrequency(freq);
                        topStems.add(existing);
                    }, () -> {
                        Stem s = new Stem();
                        s.setStem(stemValue);
                        s.setFrequency(freq);
                        topStems.add(s);
                    });
        }
        stemRepository.saveAll(topStems);


        System.out.println("✅ " + topStems.size() + " stem créés !");
    }

    private BufferedReader createSafeReader(Path file) throws IOException {

        CharsetDecoder decoder = StandardCharsets.UTF_8
                .newDecoder()
                .onMalformedInput(CodingErrorAction.IGNORE)
                .onUnmappableCharacter(CodingErrorAction.IGNORE);

        return new BufferedReader(
                new InputStreamReader(
                        Files.newInputStream(file), decoder));
    }
}
