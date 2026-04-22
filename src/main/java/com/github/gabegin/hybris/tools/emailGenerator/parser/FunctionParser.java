package com.github.gabegin.hybris.tools.emailGenerator.parser;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Function;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FunctionParser {
    private static final Pattern FUNCTION_REGEX = Pattern.compile("^\\s*(?<name>[A-Za-z]\\w*)\\s*\\(\\s*(?<rawArguments>.+?)?\\s*\\)\\s*$");
    private static final Pattern ARGUMENTS_REGEX = Pattern.compile("^\\s*(?:(?<rawArgumentNames>([A-Za-z]\\w*)(?:\\s*,\\s*([A-Za-z]\\w*))*)(?<spread>\\.\\.\\.)?)?\\s*$");
    private static final Pattern ARGUMENT_REGEX = Pattern.compile("^\\s*(?<name>[A-Za-z]\\w*)(?:\\.\\.\\.)?\\s*$");
    private static final String ARGUMENT_SEPARATOR = ",";

    public static <T> Function<T> parse(final String functionSignature, final T result) {
        if (functionSignature == null) {
            return null;
        }

        final Matcher functionMatcher = FUNCTION_REGEX.matcher(functionSignature);

        if (!functionMatcher.matches()) {
            return null;
        }

        final String name = functionMatcher.group("name");
        final String rawArguments = functionMatcher.group("rawArguments");

        if (rawArguments == null) {
            return new Function<>(name, Collections.emptyList(), null, result);
        }

        final Matcher argumentsMatcher = ARGUMENTS_REGEX.matcher(rawArguments);

        if (!argumentsMatcher.matches()) {
            return null;
        }

        final String rawArgumentNames = argumentsMatcher.group("rawArgumentNames");
        final String spread = argumentsMatcher.group("spread");

        final List<String> argumentNames = parseArgumentNames(rawArgumentNames);

        if (spread == null) {
            return new Function<>(name, argumentNames, null, result);
        }

        final List<String> conventionalArgumentNames = argumentNames.subList(0, argumentNames.size() - 1);
        final String spreader = argumentNames.getLast();

        return new Function<>(name, conventionalArgumentNames, spreader, result);
    }

    private static List<String> parseArgumentNames(final String rawArgumentNames) {
        final String[] splitArgumentNames = rawArgumentNames.split(ARGUMENT_SEPARATOR);

        return Arrays.stream(splitArgumentNames)
            .map(FunctionParser::parseArgumentName)
            .toList();
    }

    private static String parseArgumentName(final String rawArgumentName) {
        final Matcher argumentNameMatcher = ARGUMENT_REGEX.matcher(rawArgumentName);

        if (!argumentNameMatcher.matches()) {
            return null;
        }

        return argumentNameMatcher.group("name");
    }
}
