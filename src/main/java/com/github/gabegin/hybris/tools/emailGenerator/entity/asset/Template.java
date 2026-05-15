package com.github.gabegin.hybris.tools.emailGenerator.entity.asset;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
@Setter
@AllArgsConstructor
public final class Template implements Asset {
    private final Path path;
    private final String content;

    public String getSource(final String name) {
        if (this.getContent() == null) {
            return null;
        }

        final Pattern sourceRegex = Pattern.compile("^##\\s*" + name + "\\s*=\\s*(?<source>.+)$", Pattern.MULTILINE);
        final Matcher matcher = sourceRegex.matcher(this.getContent());

        if (matcher.find()) {
            return matcher.group("source");
        }

        return null;
    }
}
