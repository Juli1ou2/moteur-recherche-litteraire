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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IndexInverseBuildingService {

    private final TextProcessingService textProcessing;
    private final TextFilesReaderService textFilesReader;
    private final StemRepository stemRepository;
    private final BookRepository bookRepository;
    private final IndexInverseRepository indexInverseRepository;

    public void buildIndexInverse(Path textFilesPath) throws IOException {
        Map<String, Stem> stemCache = stemRepository.findAll()
                .stream()
                .collect(Collectors.toMap(Stem::getStem, s -> s));

        if (stemCache.isEmpty()) {
            throw new IllegalStateException(
                    "Aucun stem en base !");
        }

        List<Path> files = textFilesReader.listTextFiles(textFilesPath);
        System.out.println("📚 Index inverse — fichiers : " + files.size());
        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (Path file : files) {
            executor.submit(() -> {
                try {
                    Book book = bookRepository.findByTitre(file.getFileName().toString())
                            .orElseGet(() ->
                                    bookRepository.save(
                                            new Book(file.getFileName().toString())
                                    )
                            );

                    Map<String, Integer> termFrequencies = new HashMap<>();
                    try (BufferedReader reader = Files.newBufferedReader(file)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            for (String stem : textProcessing.extractStems(line)) {
                                termFrequencies.merge(stem, 1, Integer::sum);
                            }
                        }
                    }

                    List<IndexInverse> batch = new ArrayList<>();
                    for (Map.Entry<String, Integer> entry : termFrequencies.entrySet()) {
                        Stem stem = stemCache.get(entry.getKey());
                        if (stem != null) {
                            IndexInverse indexInverse = new IndexInverse(stem, book, entry.getValue());
                            batch.add(indexInverse);
                        }
                    }

                    indexInverseRepository.saveAll(batch);
                    System.out.println(
                            "✅ Index inverse : " + book.getTitre()
                                    + " | entrées : " + batch.size()
                    );

                } catch (Exception e) {
                    System.err.println("❌ Erreur fichier : " + file.getFileName());
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

        System.out.println("✅ Index inverse terminé !");
    }
}