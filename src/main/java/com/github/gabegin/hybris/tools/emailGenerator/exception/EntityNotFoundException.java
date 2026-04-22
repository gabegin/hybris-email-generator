package com.github.gabegin.hybris.tools.emailGenerator.exception;

import java.nio.file.Path;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(final Path path, final String modelName) {
        super("Could not find " + modelName + " '" + path.getFileName() + "' in " + path.getParent());
    }
}
