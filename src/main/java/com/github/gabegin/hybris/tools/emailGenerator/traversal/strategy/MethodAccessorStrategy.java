package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.Reflect;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.safe.SafeMethod;

public class MethodAccessorStrategy implements AccessorStrategy {
    @Override
    public Object get(final Object object, final Object attribute) {
        return new Reflect(object)
            .findMethodByName(attribute.toString())
            .map(SafeMethod::invoke)
            .orElse(null);
    }
}
