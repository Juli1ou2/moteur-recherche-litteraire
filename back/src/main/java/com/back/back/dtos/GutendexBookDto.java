package com.back.back.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Data
public class GutendexBookDto {
    private Long id;
    private String title;
    private List<String> subjects;
    private List<GutendexPersonDto> authors;
    private List<String> summaries;
    private List<GutendexPersonDto> translators;
    private List<String> bookshelves;
    private List<String> languages;
    private Boolean copyright;
    @JsonProperty("media_type")
    private String mediaType;
    private Map<String, String> formats;
    @JsonProperty("download_count")
    private Integer downloadCount;
}
