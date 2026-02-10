package com.back.back.services;

import com.back.back.entities.Stem;
import com.back.back.repositories.StemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StemBuildingService {

    private final TextFilesReaderService textFilesReader;
    private final TextProcessingService textProcessing;
    private final StemRepository stemRepository;

    public void buildStems(Path textFilesPath) throws IOException {
        Map<String, Integer> documentFrequency = new HashMap<>();
        List<Path> files = textFilesReader.listTextFiles(textFilesPath);

        for (Path file : files) {
            String content = textFilesReader.readAndClean(file);
            List<String> stems = textProcessing.extractStems(content);
            Set<String> uniqueStemsInFile = new HashSet<>(stems);

            for (String stem : uniqueStemsInFile) {
                documentFrequency.merge(stem, 1, Integer::sum);
            }
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
    }
}

