package com.back.back.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
public class TextFilesReaderService {
    public List<Path> listTextFiles(Path root) throws IOException {
        try (Stream<Path> paths = Files.walk(root)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".txt"))
                    .toList();
        }
    }

    public String readAndClean(Path path) throws IOException {
        String raw = Files.readString(path);

        return raw
                .replaceAll("(?s).*?\\*\\*\\* START OF THIS PROJECT GUTENBERG EBOOK .*?\\*\\*\\*", "")
                .replaceAll("(?s)\\*\\*\\* END OF THIS PROJECT GUTENBERG EBOOK .*", "")
                .trim();
    }
}
