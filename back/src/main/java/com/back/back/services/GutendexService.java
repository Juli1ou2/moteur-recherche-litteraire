package com.back.back.services;

import com.back.back.dtos.GutendexBookDto;
import com.back.back.dtos.GutendexResultDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class GutendexService {

    private final RestTemplate restTemplate = new RestTemplate();

    public Optional<List<GutendexBookDto>> getBooksMetadataFromIds(String ids) {
        try {
            String url = "https://gutendex.com/books/?ids=" + ids;

            GutendexResultDto response =
                    restTemplate.getForObject(url, GutendexResultDto.class);

            return Optional.ofNullable(response.getResults());

        } catch (Exception e) {
            System.err.println("❌ Gutendex indisponible pour " + ids);
            return Optional.empty();
        }
    }
}