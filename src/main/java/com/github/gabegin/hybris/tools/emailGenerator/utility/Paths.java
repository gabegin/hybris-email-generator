package com.github.gabegin.hybris.tools.emailGenerator.utility;

import java.nio.file.Path;

public class Paths {
    public static Path rebase(final Path path, final Path base) {
        if (path == null || base == null) {
            return null;
        }

        if (hasRelativeRoot(base)) {
            return rebase(path, removeRelativeRoot(base));
        }

        final Path root = base.iterator().next();

        if (path.endsWith(root)) {
            return path.getParent().resolve(base);
        }

        return rebase(path.getParent(), base);
    }

    private static boolean hasRelativeRoot(final Path path) {
        return path.startsWith("/");
    }

    private static Path removeRelativeRoot(final Path path) {
        return Path.of(path.toString().substring(1));
    }
}
