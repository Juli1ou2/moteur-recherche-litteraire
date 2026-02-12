package com.back.back.dtos;

import lombok.Data;

import java.util.List;

@Data
public class GutendexResultDto {
    private Integer count;
    private String next;
    private String previous;
    private List<GutendexBookDto> results;
}
