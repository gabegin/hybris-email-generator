package com.github.gabegin.hybris.tools.emailGenerator.utility;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Function;
import com.github.gabegin.hybris.tools.emailGenerator.extension.instrospection.ModelMethod;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.Accessor;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.Traverser;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Templates {
    private static final Pattern EXPRESSION_REGEX = Pattern.compile("(?<!\\\\)\\{(?<expression>(?:\\\\}|[^}])+)}");

    public static String apply(final String template, final ModelMethod<?> modelMethod, final Object object, final Object... values) {
        final String[] templateParts = template.splitWithDelimiters(EXPRESSION_REGEX.pattern(), 0);

        if (templateParts.length == 1) {
            return templateParts[0];
        }

        final Map<String, Object> context = createContext(modelMethod, object, values);

        return Arrays.stream(templateParts)
            .map((templatePart) -> replaceTemplatePart(templatePart, context))
            .collect(Collectors.joining());
    }

    private static String replaceTemplatePart(final String templatePart, final Map<String, Object> context) {
        final Matcher matcher = EXPRESSION_REGEX.matcher(templatePart);

        if (!matcher.matches()) {
            return unescapeTemplate(templatePart);
        }

        final String expression = matcher.group("expression");
        final Object result = new Traverser(context).traverse(expression);

        if (result == null) {
            return null;
        }

        return result.toString();
    }

    private static String unescapeTemplate(final String expression) {
        return expression.replaceAll("\\\\([{}\\\\])", "$1");
    }

    private static Map<String, Object> createContext(final ModelMethod<?> modelMethod, final Object object, final Object... values) {
        final Map<String, Object> context = new HashMap<>();
        final Accessor accessor = new Accessor(object);

        context.putAll(modelMethod.model());
        context.putAll(accessor.entries());

        putArgumentValues(context, modelMethod, values);

        context.put("@", object);
        context.put("$", modelMethod.model());

        return context;
    }

    private static void putArgumentValues(final Map<String, Object> context, final ModelMethod<?> modelMethod, final Object... values) {
        final Function<?> function = modelMethod.function();
        final List<String> arguments = function.arguments();

        for (int index = 0; index < arguments.size(); index++) {
            final String argument = arguments.get(index);
            final Object value = values[index];

            context.put(argument, value);
        }

        if (!function.spread()) {
            return;
        }

        final Object[] spreadValues = Arrays.stream(values)
            .skip(function.arity())
            .toArray();

        context.put(function.spreader(), spreadValues);
    }
}
