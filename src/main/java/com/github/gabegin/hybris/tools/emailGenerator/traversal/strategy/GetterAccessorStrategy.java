package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.Reflect;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeMethod;
import com.google.auto.service.AutoService;

@AutoService(AccessorStrategy.class)
public class GetterAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        return new Reflect(object)
            .findMethodByName(attribute.toString(), "get", "is")
            .map(SafeMethod::invoke)
            .orElse(null);
    }
}
