package com.github.gabegin.hybris.tools.emailGenerator.entity;

import org.apache.commons.io.FilenameUtils;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public record Template(Path path, String content) {
    public String getSource(final String name) {
        final Pattern sourceRegex = Pattern.compile("^##\\s*" + name + "\\s*=\\s*(?<source>.+)$", Pattern.MULTILINE);
        final Matcher matcher = sourceRegex.matcher(this.content());

        if (matcher.find()) {
            return matcher.group("source");
        }

        return null;
    }

    public String getFilename() {
        return this.path().getFileName().toString();
    }

    public String getName() {
        return FilenameUtils.getBaseName(this.getFilename());
    }
}
