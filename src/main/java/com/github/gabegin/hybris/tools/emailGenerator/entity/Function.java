package com.github.gabegin.hybris.tools.emailGenerator.entity;

import java.util.List;

public record Function<T>(String name, List<String> arguments, String spreader, T result) {
    public int arity() {
        return this.arguments().size();
    }

    public boolean spread() {
        return this.spreader() != null;
    }

    public boolean accepts(final Object... arguments) {
        final int arity = this.arity();

        if (this.spread()) {
            return arguments.length >= arity;
        }

        return arguments.length == arity;
    }
}
