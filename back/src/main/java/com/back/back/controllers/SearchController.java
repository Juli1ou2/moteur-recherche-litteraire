package com.back.back.controllers;

import com.back.back.entities.Book;
import com.back.back.entities.Stem;
import com.back.back.repositories.BookRepository;
import com.back.back.repositories.IndexInverseRepository;
import com.back.back.repositories.StemRepository;
import com.back.back.services.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class SearchController {
    private final StemRepository stemRepository;
    private final BookRepository bookRepository;
    private final IndexInverseRepository indexInverseRepository;
    private final ScoringService scoringService;

    @GetMapping("/search")
    public String index() {
        return "Bienvenue sur Shajudan Search !";
    }

    @GetMapping("/search/stem")
    public List<Map<String, Object>> searchStem(
            @RequestParam("word") String word,
            @RequestParam(value = "maxResult", defaultValue = "10") int maxResult
    ) {
        List<Stem> stems = stemRepository.findAll();
        return scoringService.getStemFromSearch(stems, word, maxResult);
    }

    @GetMapping("/search/book")
    public String searchBookFromStem(@RequestParam("word") String word) {
        List<Stem> stems = stemRepository.findAll();
        Map<String, Object> stemResult = scoringService.getStemFromSearch(stems, word, 1).getFirst();

        Long idSearchedBook = indexInverseRepository.getBookIdByStem((Long) stemResult.get("stemId"));
        Book searchedBook = bookRepository.findById(idSearchedBook).orElse(new Book());

        return searchedBook.getTitre();
    }
}
