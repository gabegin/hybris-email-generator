package com.github.gabegin.hybris.tools.emailGenerator.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public final class Function<T> {
    private final String name;
    private final List<String> arguments;
    private final String spreader;
    private final T result;

    public boolean accepts(final Object... arguments) {
        final int arity = this.getArity();

        if (this.isSpread()) {
            return arguments.length >= arity;
        }

        return arguments.length == arity;
    }

    public int getArity() {
        return this.getArguments().size();
    }

    public boolean isSpread() {
        return this.getSpreader() != null;
    }
}
