package com.github.gabegin.hybris.tools.emailGenerator.utility;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Objects {
    public static List<String> asList(final Object object) {
        if (object instanceof final Object[] array) {
            return Arrays.stream(array).map(Object::toString).toList();
        }

        if (object instanceof final Collection<?> collection) {
            return collection.stream().map(Object::toString).toList();
        }

        return Collections.emptyList();
    }
}
