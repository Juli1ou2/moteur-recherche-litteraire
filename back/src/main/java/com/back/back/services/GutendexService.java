package com.back.back.services;

import com.back.back.dtos.GutendexBookDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<GutendexBookDto> getBooksMetadataFromIds(String ids) {
        try {
            String url = "https://gutendex.com/books/?ids=" + ids;

            GutendexBookDto response =
                    restTemplate.getForObject(url, GutendexBookDto.class);

            return Optional.ofNullable(response);

        } catch (Exception e) {
            System.err.println("❌ Gutendex indisponible pour " + ids);
            return Optional.empty();
        }
    }
}