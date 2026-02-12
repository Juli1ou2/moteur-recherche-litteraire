package com.back.back;

import com.back.back.services.IndexInverseBuildingService;
import com.back.back.services.StemBuildingService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class IndexingRunner implements CommandLineRunner {
    private final StemBuildingService stemBuildingService;
    private final IndexInverseBuildingService indexInverseBuildingService;

    @Value("${gutenberg.stemming.indexing}")
    private boolean enabled;

    @Value("${gutenberg.path}")
    private String path;

    Resource resource = new ClassPathResource("data/gutenberg_books");

    @Override
    public void run(String... args) throws Exception {
//        Path textFilesPath = resource.getFile().toPath();
        Path textFilesPath = Paths.get(path);

        if (enabled) {
            stemBuildingService.buildStems(textFilesPath);
            indexInverseBuildingService.buildIndexInverse(textFilesPath);
        }
    }
}
