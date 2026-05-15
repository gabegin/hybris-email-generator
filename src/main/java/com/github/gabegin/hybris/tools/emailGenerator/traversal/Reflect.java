package com.github.gabegin.hybris.tools.emailGenerator.traversal;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeField;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeMethod;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

@Getter
@AllArgsConstructor
public final class Reflect {
    private final Object object;

    public Optional<SafeField> findField(final String name) {
        final Field[] fields = this.getObject().getClass().getFields();

        return Arrays.stream(fields)
            .filter((field) -> field.getName().equals(name))
            .findFirst()
            .map((field) -> new SafeField(field, this.getObject()));
    }

    public Optional<SafeMethod> findMethod(final String name, final Class<?>... parameterTypes) {
        final Method[] methods = this.getObject().getClass().getMethods();

        return Arrays.stream(methods)
            .filter((method) -> method.getName().equals(name))
            .filter((method) -> this.hasAssignableParameters(method, parameterTypes))
            .findFirst()
            .map((method) -> new SafeMethod(method, this.getObject()));
    }

    public Optional<SafeMethod> findMethodByName(final String name, final String... prefixes) {
        final Method[] methods = this.getObject().getClass().getMethods();

        return Arrays.stream(methods)
            .filter((method) -> this.hasName(method, name, prefixes))
            .findFirst()
            .map((method) -> new SafeMethod(method, this.getObject()));
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
