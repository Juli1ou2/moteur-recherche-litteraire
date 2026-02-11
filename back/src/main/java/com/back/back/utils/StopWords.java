package com.back.back.utils;

import java.util.Set;

public class StopWords {

    private static final Set<String> ENGLISH = Set.of(
            "the", "a", "an", "and", "or", "but", "if", "then", "else",
            "when", "where", "while", "of", "to", "in", "on", "for",
            "with", "without", "by", "about", "against", "between",
            "into", "through", "during", "before", "after", "above", "below",
            "is", "are", "was", "were", "be", "been", "being",
            "have", "has", "had", "do", "does", "did",
            "this", "that", "these", "those",
            "i", "you", "he", "she", "it", "we", "they",
            "me", "him", "her", "them", "my", "your", "his", "their"
    );

    public static boolean isStopWord(String token) {
        return ENGLISH.contains(token);
    }
}
