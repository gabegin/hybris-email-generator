package com.github.gabegin.hybris.tools.emailGenerator.traversal;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy.AccessorStrategy;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Objects;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.gabegin.hybris.tools.emailGenerator.utility.Strategies.tryWith;

public record Accessor(Object object) {
    public Object get(final Object attribute) {
        return tryWith(AccessorStrategy.class).get(this.object(), attribute);
    }

    public Map<String, Object> entries() {
        final List<String> keys = this.keys();

        return keys.stream().collect(Collectors.toMap(Function.identity(), this::get));
    }

    public List<String> keys() {
        return Optional
            .ofNullable(this.getKeys())
            .map(Objects::asList)
            .orElseGet(Collections::emptyList);
    }

    private Object getKeys() {
        final Object keys = this.get("keys");

        if (keys != null) {
            return keys;
        }

        return this.get("keySet");
    }
}
