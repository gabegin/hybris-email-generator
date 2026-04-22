package com.github.gabegin.hybris.tools.emailGenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.util.Properties;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

@Getter
@AllArgsConstructor
public class ResourceBundle extends Properties {
    private final Path path;

    public String getMessage(final String key, final Object... arguments) {
        final String message = this.getProperty(key);

        if (message == null || arguments == null || arguments.length == 0) {
            return message;
        }

        return Pattern.compile("\\{(\\d+)}")
            .matcher(message)
            .replaceAll((match) -> this.replaceArgument(match, arguments));
    }

    private String replaceArgument(final MatchResult match, final Object... arguments) {
        final int index = Integer.parseInt(match.group(1));

        if (index >= arguments.length) {
            return match.group();
        }

        return arguments[index].toString();
    }
}
