package com.github.gabegin.hybris.tools.emailGenerator.traversal.safe;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public record SafeMethod(Method method, Object object) {
    public Object invoke(final Object... arguments) {
        try {
            return this.method().invoke(this.object(), arguments);
        } catch (final IllegalAccessException | InvocationTargetException ignored) {
            return null;
        }
    }
}
