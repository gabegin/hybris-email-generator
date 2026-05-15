package com.github.gabegin.hybris.tools.emailGenerator.entity.function;

import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Model;
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

    @SuppressWarnings("unchecked")
    public T execute(final Model model, final Object object, final Object... arguments) {
        final T result = this.getResult();

        if (!(result instanceof String)) {
            return result;
        }

        return (T) new Expression((Function<String>) this).evaluate(model, object, arguments);
    }

    public int getArity() {
        return this.getArguments().size();
    }

    public boolean isSpread() {
        return this.getSpreader() != null;
    }
}
