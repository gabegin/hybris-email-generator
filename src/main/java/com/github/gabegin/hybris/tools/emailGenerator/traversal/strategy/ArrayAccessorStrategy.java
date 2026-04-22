package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.google.auto.service.AutoService;

@AutoService(AccessorStrategy.class)
public class ArrayAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        if (object instanceof final Object[] array && attribute instanceof final Integer index) {
            return array[index];
        }

        return null;
    }
}
