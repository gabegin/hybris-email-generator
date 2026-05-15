package com.github.gabegin.hybris.tools.emailGenerator.entity.function;

import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Model;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.Accessor;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.Traverser;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter(AccessLevel.PROTECTED)
@AllArgsConstructor
public class Expression {
    private static final Pattern INTERPOLATION_REGEX = Pattern.compile("(?<!\\\\)\\{(?<attribute>(?:\\\\}|[^}])+)}");

    private final Function<String> function;

    public String evaluate(final Model model, final Object object, final Object[] values) {
        final String[] placeholders = this.getFunction().getResult().splitWithDelimiters(INTERPOLATION_REGEX.pattern(), 0);

        if (placeholders.length == 1) {
            return placeholders[0];
        }

        final Map<String, Object> context = this.createContext(model, object, values);

        return Arrays.stream(placeholders)
            .map((placeholder) -> interpolate(placeholder, context))
            .collect(Collectors.joining());
    }

    private Map<String, Object> createContext(final Model model, final Object object, final Object[] values) {
        final Map<String, Object> context = new HashMap<>(model);

        if (!object.equals(model)) {
            context.putAll(new Accessor(object).getEntries());
        }

        this.insertValues(context, values);

        context.put("@", object);
        context.put("$", model);

        return context;
    }

    private void insertValues(final Map<String, Object> context, final Object[] values) {
        final Function<String> function = this.getFunction();
        final List<String> arguments = function.getArguments();

        for (int index = 0; index < arguments.size(); index++) {
            final String argument = arguments.get(index);
            final Object value = values[index];

            context.put(argument, value);
        }

        if (!function.isSpread()) {
            return;
        }

        final Object[] spreadValues = Arrays.stream(values)
            .skip(function.getArity())
            .toArray();

        context.put(function.getSpreader(), spreadValues);
    }

    private String interpolate(final String placeholder, final Map<String, Object> context) {
        final Matcher matcher = INTERPOLATION_REGEX.matcher(placeholder);

        if (!matcher.matches()) {
            return this.unescape(placeholder);
        }

        final String attribute = matcher.group("attribute");
        final Object result = new Traverser(context).traverse(attribute);

        if (result == null) {
            return "";
        }

        return result.toString();
    }

    private String unescape(final String placeholder) {
        return placeholder.replaceAll("\\\\([{}\\\\])", "$1");
    }
}
