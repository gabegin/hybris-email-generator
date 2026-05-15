package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Asset;
import com.github.gabegin.hybris.tools.emailGenerator.exception.UnknownEntityException;
import lombok.SneakyThrows;

import java.nio.file.Files;
import java.nio.file.Path;

public interface AssetLoader<T extends Asset> {
    Path getDirectory();
    String getExtension();
    String getName();
    Class<T> getType();

    T load(final Path path, final String content);

    @SneakyThrows
    default T load() {
        final Path path = this.getPath();

        if (path == null || !path.toFile().exists()) {
            return this.loadEmptyAsset();
        }

        final String content = Files.readString(path);

        return this.load(path, content);
    }

    default T loadEmptyAsset() {
        throw new UnknownEntityException(this.getEntityName());
    }

    default String resolveEmptyName() {
        throw new UnknownEntityException(this.getEntityName());
    }

    private Path getPath() {
        final String filename = this.getFilename();

        if (filename == null) {
            return null;
        }

        final Path path = Path.of(filename);

        if (path.isAbsolute()) {
            return path;
        }

        final Path directory = this.getDirectory();

        if (directory != null) {
            return directory.resolve(path);
        }

        return Path.of("").resolve(path);
    }

    private String getFilename() {
        final String name = this.resolveName();

        if (name == null) {
            return null;
        }

        final String extension = this.getExtension();

        if (name.endsWith(extension)) {
            return name;
        }

        return name + extension;
    }

    private String resolveName() {
        final String name = this.getName();

        if (name != null) {
            return name;
        }

        return this.resolveEmptyName();
    }

    private String getEntityName() {
        return this.getType().getSimpleName();
    }
}
