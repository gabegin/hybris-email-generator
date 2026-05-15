package com.github.gabegin.hybris.tools.emailGenerator.traversal;

import com.github.gabegin.hybris.tools.emailGenerator.traversal.strategy.AccessorStrategy;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.github.gabegin.hybris.tools.emailGenerator.utility.Strategies.tryWith;

@Getter
@AllArgsConstructor
public final class Accessor {
    private final Object object;

    public Object get(final Object attribute) {
        return tryWith(AccessorStrategy.class).get(this.getObject(), attribute);
    }

    public Map<String, Object> getEntries() {
        final List<String> keys = this.getKeys();

        return keys.stream().collect(Collectors.toMap(Function.identity(), this::get));
    }

    public List<String> getKeys() {
        return Optional
            .ofNullable(this.tryGetKeys())
            .map(Objects::asList)
            .orElseGet(Collections::emptyList);
    }

    private Object tryGetKeys() {
        final Object keys = this.get("keys");

        if (keys != null) {
            return keys;
        }

        return this.get("keySet");
    }
}
