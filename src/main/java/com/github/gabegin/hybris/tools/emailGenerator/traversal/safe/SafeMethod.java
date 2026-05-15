package com.github.gabegin.hybris.tools.emailGenerator.traversal.safe;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public final class SafeMethod {
    private final Method method;
    private final Object object;

    public Object invoke(final Object... arguments) {
        try {
            return this.getMethod().invoke(this.getObject(), arguments);
        } catch (final IllegalAccessException | InvocationTargetException ignored) {
            return null;
        }
    }
}
