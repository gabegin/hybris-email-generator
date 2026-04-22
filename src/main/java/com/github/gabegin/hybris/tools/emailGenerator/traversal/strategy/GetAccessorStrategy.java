package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.Reflect;
import com.google.auto.service.AutoService;

@AutoService(AccessorStrategy.class)
public class GetAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        return new Reflect(object)
            .findMethod("get", attribute.getClass())
            .map((method) -> method.invoke(attribute))
            .orElse(null);
    }
}
