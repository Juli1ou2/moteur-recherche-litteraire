package com.back.back.services;

import com.back.back.entities.Stem;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ScoringService {
    public int distanceLevenshtein(String a, String b) {
        if (a == null) a = "";
        if (b == null) b = "";

        int n = a.length();
        int m = b.length();
        int[][] dp = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) dp[i][0] = i;
        for (int j = 0; j <= m; j++) dp[0][j] = j;

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                int cost = (a.charAt(i - 1) == b.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(
                        Math.min(dp[i - 1][j] + 1,
                                dp[i][j - 1] + 1),
                        dp[i - 1][j - 1] + cost
                );
            }
        }
        return dp[n][m];
    }

    public double similitudeLevenshtein(String a, String b) {
        int dist = distanceLevenshtein(a, b);
        int maxLen = Math.max(a.length(), b.length());
        if (maxLen == 0) return 1.0;
        return 1.0 - ((double) dist / maxLen);
    }

    public double similitudeJaccard(String a, String b) {
        if (a == null || b == null) return 0.0;

        Set<String> setA = new HashSet<>(Arrays.asList(a.split("")));
        Set<String> setB = new HashSet<>(Arrays.asList(b.split("")));

        Set<String> intersection = new HashSet<>(setA);
        intersection.retainAll(setB);

        Set<String> union = new HashSet<>(setA);
        union.addAll(setB);

        if (union.isEmpty()) return 1.0;
        return (double) intersection.size() / union.size();
    }

    public String autoCorrect(String input, Set<String> vocabulary) {
        String best = input;
        double bestScore = -1;

        for (String candidate : vocabulary) {
            double score = similitudeLevenshtein(input.toLowerCase(), candidate.toLowerCase());
            if (score > bestScore) {
                bestScore = score;
                best = candidate;
            }
        }
        return best;
    }

    public List<Map<String, Object>> getStemFromSearch(List<Stem> stems, String word, int maxResult) {
        return stems.stream().map(stem -> {
                    double levenshtein = similitudeLevenshtein(word, stem.getStem());
                    double jaccard = similitudeJaccard(word, stem.getStem());
                    double score = (levenshtein + jaccard) / 2.0;
                    Map<String, Object> map = new HashMap<>();
                    map.put("stem", stem.getStem());
                    map.put("stemId", stem.getId());
                    map.put("frequency", stem.getFrequency());
                    map.put("levenshtein", levenshtein);
                    map.put("jaccard", jaccard);
                    map.put("score", score);
                    return map;
                }).sorted((a, b) -> Double.compare((double) b.get("score"), (double) a.get("score")))
                .limit(maxResult)
                .collect(Collectors.toList());
    }
}
