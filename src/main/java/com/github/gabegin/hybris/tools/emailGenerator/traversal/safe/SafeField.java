package com.github.gabegin.hybris.tools.emailGenerator.traversal.safe;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.lang.reflect.Field;

@Getter
@AllArgsConstructor
public final class SafeField {
    private final Field field;
    private final Object object;

    public Object get() {
        try {
            return this.getField().get(this.getObject());
        } catch (final IllegalAccessException ignored) {
            return null;
        }
    }
}
