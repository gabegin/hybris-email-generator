package com.github.gabegin.hybris.tools.emailGenerator.utility;

import com.github.gabegin.hybris.tools.emailGenerator.Configuration;

import java.nio.file.Path;

public class Sources {
    private static final String CLASSPATH_PREFIX = "classpath:";
    private static final String LANGUAGE_VARIABLE = "$lang";

    public static String resolve(final Path path, final String source) {
        if (source == null || !source.startsWith(CLASSPATH_PREFIX)) {
            return source;
        }

        final String classpath = getClasspath(source);
        final Path sourcePath = Path.of(classpath);

        return Paths.rebase(path, sourcePath).toString();
    }

    private static String getClasspath(final String source) {
        return source
            .substring(CLASSPATH_PREFIX.length())
            .replace(LANGUAGE_VARIABLE, Configuration.get().getLanguage().toString());
    }
}
