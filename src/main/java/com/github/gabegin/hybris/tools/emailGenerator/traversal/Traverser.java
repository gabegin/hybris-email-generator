package com.github.gabegin.hybris.tools.emailGenerator.traversal;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Iterator;

@Getter
@AllArgsConstructor
public final class Traverser {
    private final Object object;

    public Object traverse(final String path) {
        final Iterator<String> breadcrumbs = this.getBreadcrumbs(path);

        Object result = this.getObject();

        while (breadcrumbs.hasNext() && result != null) {
            result = new Accessor(result).get(breadcrumbs.next());
        }

        return result;
    }

    private Iterator<String> getBreadcrumbs(final String path) {
        final String[] breadcrumbs = path.replaceAll("\\[(\\d+)]", ".$1").split("[./\\\\]");

        return Arrays.stream(breadcrumbs).iterator();
    }
}
