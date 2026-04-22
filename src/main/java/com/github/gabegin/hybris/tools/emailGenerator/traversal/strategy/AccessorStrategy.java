package com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy;

public interface AccessorStrategy {
    Object get(Object object, Object attribute);
}
