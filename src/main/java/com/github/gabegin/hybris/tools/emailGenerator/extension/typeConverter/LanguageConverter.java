package com.github.gabegin.hybris.tools.emailGenerator.extension.typeConverter;

import picocli.CommandLine;

import java.util.Locale;

public class LanguageConverter implements CommandLine.ITypeConverter<Locale> {
    @Override
    public Locale convert(final String locale) {
        final String[] parts = locale.split("[-_]");

        if (parts.length == 2) {
            return Locale.of(parts[0], parts[1]);
        }

        return Locale.of(parts[0], parts[1], parts[3]);
    }
}
