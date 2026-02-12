package com.back.back.controllers;

import com.back.back.dtos.GutendexBookDto;
import com.back.back.entities.Book;
import com.back.back.entities.Stem;
import com.back.back.repositories.BookRepository;
import com.back.back.repositories.IndexInverseRepository;
import com.back.back.repositories.StemRepository;
import com.back.back.services.GutendexService;
import com.back.back.services.ScoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class SearchController {
    private final StemRepository stemRepository;
    private final BookRepository bookRepository;
    private final IndexInverseRepository indexInverseRepository;
    private final ScoringService scoringService;
    private final GutendexService gutendexService;

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

    @GetMapping("/search/books")
    public List<Book> searchBooksFromStem(@RequestParam("word") String word) {
        List<Stem> stems = stemRepository.findAll();
        Map<String, Object> stemResult = scoringService.getStemFromSearch(stems, word, 1).getFirst();

        List<Long> idSearchedBooks = indexInverseRepository.getBookIdByStem((Long) stemResult.get("stemId"));
        return bookRepository.findAllById(idSearchedBooks).stream().toList().subList(0, 9);
    }

    @GetMapping("/search/books/gutendex")
    public Optional<GutendexBookDto> searchBooksFromStemAndGutenberg(@RequestParam("word") String word) {
        List<Stem> stems = stemRepository.findAll();
        Map<String, Object> stemResult = scoringService.getStemFromSearch(stems, word, 1).getFirst();

        List<Long> idSearchedBooks = indexInverseRepository.getBookIdByStem((Long) stemResult.get("stemId"));
        List<Book> books = bookRepository
                .findAllById(idSearchedBooks)
                .stream()
                .limit(9)
                .toList();

        String booksIds = books.stream()
                .map(Book::getTitre)
                .map(titre -> titre.endsWith(".txt")
                        ? titre.substring(0, titre.length() - 4)
                        : titre)
                .collect(Collectors.joining(","));

        return gutendexService.getBooksMetadataFromIds(booksIds);
    }
}
