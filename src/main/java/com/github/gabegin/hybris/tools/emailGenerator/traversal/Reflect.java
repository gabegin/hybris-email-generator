package com.github.gabegin.hybris.tools.emailGenerator.traversal;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeField;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeMethod;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

public record Reflect(Object object) {
    public Optional<SafeField> findField(final String name) {
        final Field[] fields = this.object().getClass().getFields();

        return Arrays.stream(fields)
            .filter((field) -> field.getName().equals(name))
            .findFirst()
            .map((field) -> new SafeField(field, this.object()));
    }

    public Optional<SafeMethod> findMethod(final String name, final Class<?>... parameterTypes) {
        final Method[] methods = this.object().getClass().getMethods();

        return Arrays.stream(methods)
            .filter((method) -> method.getName().equals(name))
            .filter((method) -> this.hasAssignableParameters(method, parameterTypes))
            .findFirst()
            .map((method) -> new SafeMethod(method, this.object()));
    }

    public Optional<SafeMethod> findMethodByName(final String name, final String... prefixes) {
        final Method[] methods = this.object().getClass().getMethods();

        return Arrays.stream(methods)
            .filter((method) -> this.hasName(method, name, prefixes))
            .findFirst()
            .map((method) -> new SafeMethod(method, this.object()));
    }

    private boolean hasAssignableParameters(final Method method, final Class<?>[] compareTypes) {
        final Class<?>[] types = method.getParameterTypes();

        if (types.length != compareTypes.length) {
            return false;
        }

        return IntStream.range(0, types.length).allMatch(index -> types[index].isAssignableFrom(compareTypes[index]));
    }

    private boolean hasName(final Method method, final String name, final String... prefixes) {
        final String methodName = method.getName();

        if (prefixes.length == 0) {
            return methodName.equals(name);
        }

        return Arrays.stream(prefixes).anyMatch((prefix) -> {
            return methodName.equals(prefix + name)
                || methodName.equals(prefix + StringUtils.capitalize(name));
        });
    }
}
