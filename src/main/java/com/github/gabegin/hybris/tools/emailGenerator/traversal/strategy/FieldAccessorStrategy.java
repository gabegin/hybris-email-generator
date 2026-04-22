package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.Reflect;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeField;
import com.google.auto.service.AutoService;

@AutoService(AccessorStrategy.class)
public class FieldAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        return new Reflect(object)
            .findField(attribute.toString())
            .map(SafeField::get)
            .orElse(null);
    }
}
