package com.back.back.services;

import com.back.back.entities.Book;
import com.back.back.entities.IndexInverse;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class StemBuildingService {

    private final TextFilesReaderService textFilesReader;
    private final TextProcessingService textProcessing;
    private final StemRepository stemRepository;
    private final BookRepository bookRepository;
    private final IndexInverseRepository indexInverseRepository;

    public void buildStems(Path textFilesPath) throws IOException {
        Map<String, Integer> documentFrequency = new ConcurrentHashMap<>();
        List<Path> files = textFilesReader.listTextFiles(textFilesPath);
        System.out.println("Nombre de fichiers : " + files.size());
        ExecutorService executor = Executors.newFixedThreadPool(4);
        AtomicInteger fileCount = new AtomicInteger(1);

        for (Path file : files) {
            Book book = new Book(file.getFileName().toString());
            bookRepository.save(book);
            executor.submit(() -> {
                try {
                    Set<String> uniqueStemsInFile = new HashSet<>();

                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            List<String> stems = textProcessing.extractStems(line);
                            uniqueStemsInFile.addAll(stems);
                        }
                    }

                    for (String stem : uniqueStemsInFile) {
                        documentFrequency.merge(stem, 1, Integer::sum);
                        indexInverseRepository.save(new IndexInverse(stem, book, documentFrequency.get(stem)));
                    }

                    System.out.println(fileCount.getAndIncrement() + " : traitement terminé : "
                            + file.getFileName() + ", stems uniques : " + uniqueStemsInFile.size());

                } catch (Exception e) {
                    System.err.println("Erreur fichier " + file.getFileName() + " : " + e.getMessage());
                }
            });
        }

        executor.shutdown();
        try {
            executor.awaitTermination(2, TimeUnit.HOURS);
        } catch (InterruptedException e) {
            System.err.println("Traitement interrompu : " + e.getMessage());
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
        System.out.println("Indexation terminée, stems insérés : " + topStems.size());
    }
}

