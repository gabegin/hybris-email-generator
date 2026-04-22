package com.github.gabegin.hybris.tools.emailGenerator.writer;

import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public record FileWriter(Path path) implements Writer {
    @Override
    @SneakyThrows
    public void write(final String content) {
        final Path directory = this.path().getParent();

        if (directory != null && !directory.toFile().exists()) {
            Files.createDirectories(directory);
        }

        Files.writeString(this.path(), content);
    }
}
