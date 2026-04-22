package com.github.gabegin.hybris.tools.emailGenerator.exception;

public class UnknownEntityException extends RuntimeException {
    public UnknownEntityException(final String modelName) {
        super("No " + modelName + " specified");
    }
}
