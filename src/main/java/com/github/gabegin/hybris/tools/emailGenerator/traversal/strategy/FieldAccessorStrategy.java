package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.Reflect;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeField;

public class FieldAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        return new Reflect(object)
            .findField(attribute.toString())
            .map(SafeField::get)
            .orElse(null);
    }
}
