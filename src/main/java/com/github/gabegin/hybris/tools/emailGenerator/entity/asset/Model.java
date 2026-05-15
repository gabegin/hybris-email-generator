package com.github.gabegin.hybris.tools.emailGenerator.entity.asset;

import lombok.Getter;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
public final class Model extends HashMap<String, Object> implements Asset {
    private final Path path;

    public Model(final Path path, final Map<String, Object> attributes) {
        super(attributes);

        this.path = path;
    }
}
