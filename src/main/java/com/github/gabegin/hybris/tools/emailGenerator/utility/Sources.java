package com.github.gabegin.hybris.tools.emailGenerator.utility;

import java.nio.file.Path;
import java.util.Locale;

public class Sources {
    private static final String CLASSPATH_PREFIX = "classpath:";
    private static final String LANGUAGE_VARIABLE = "$lang";

    public static String resolve(final Path path, final String source, final Locale language) {
        if (source == null || !source.startsWith(CLASSPATH_PREFIX)) {
            return source;
        }

        final String classpath = getClasspath(source, language);
        final Path sourcePath = Path.of(classpath);

        return Paths.rebase(path, sourcePath).toString();
    }

    private static String getClasspath(final String source, final Locale language) {
        return source
            .substring(CLASSPATH_PREFIX.length())
            .replace(LANGUAGE_VARIABLE, language.toString());
    }
}
