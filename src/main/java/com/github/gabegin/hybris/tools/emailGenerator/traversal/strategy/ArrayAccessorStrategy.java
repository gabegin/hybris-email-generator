package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.google.auto.service.AutoService;

@AutoService(AccessorStrategy.class)
public class ArrayAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        final Object integerAttribute = this.tryParseInteger(attribute);

        if (object instanceof final Object[] array && integerAttribute instanceof final Integer index) {
            return array[index];
        }

        return null;
    }

    private Object tryParseInteger(final Object index) {
        if (!(index instanceof String)) {
            return index;
        }

        try {
            return Integer.parseInt((String) index);
        } catch (final NumberFormatException e) {
            return index;
        }
    }
}
