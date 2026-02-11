package com.back.back.services;

import com.back.back.utils.StopWords;
import org.springframework.stereotype.Service;
import org.tartarus.snowball.ext.englishStemmer;

import java.util.ArrayList;
import java.util.List;

@Service
public class TextProcessingService {

    public List<String> extractStems(String text) {

        englishStemmer stemmer = new englishStemmer();

        String[] tokens = text
                .toLowerCase()
                .replaceAll("[^a-z ]", " ")
                .split("\\s+");

        List<String> stems = new ArrayList<>();

        for (String token : tokens) {
            if (token.length() < 3) continue;
            if (StopWords.isStopWord(token)) continue;

            stemmer.setCurrent(token);
            stemmer.stem();

            String stem = stemmer.getCurrent();

            // sécurité minimale
            if (stem.length() >= 3) {
                stems.add(stem);
            }
        }

        return stems;
    }
}
