package com.github.gabegin.hybris.tools.emailGenerator.traversal.safe;

import java.lang.reflect.Field;

public record SafeField(Field field, Object object) {
    public Object get() {
        try {
            return this.field().get(this.object());
        } catch (final IllegalAccessException ignored) {
            return null;
        }
    }
}
