package com.back.back.services;

import com.back.back.entities.Stem;
import com.back.back.repositories.BookRepository;
import com.back.back.repositories.IndexInverseRepository;
import com.back.back.repositories.StemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

                    try (BufferedReader reader = Files.newBufferedReader(file)) {
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

        List<Stem> topStems = documentFrequency.entrySet()
                .stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10_000)
                .map(e -> {
                    Stem s = new Stem();
                    s.setStem(e.getKey());
                    s.setFrequency(e.getValue());
                    return s;
                })
                .toList();

        stemRepository.saveAll(topStems);

        System.out.println("✅ " + topStems.size() + " stem créés !");
    }
}
