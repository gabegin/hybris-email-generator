package com.github.gabegin.hybris.tools.emailGenerator.extension.instrospection;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Function;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Model;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Templates;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.VelMethod;

import java.lang.reflect.Method;

public record ModelMethod<T>(Function<T> function, Model model) implements VelMethod {
    @Override
    public Object invoke(final Object object, final Object... arguments) {
        final T result = this.function().result();

        if (!(result instanceof final String template)) {
            return result;
        }

        if (object instanceof Context) {
            return Templates.apply(template, this, this.model(), arguments);
        }

        return Templates.apply(template, this, object, arguments);
    }

    @Override
    public boolean isCacheable() {
        return false;
    }

    @Override
    public String getMethodName() {
        return this.function().name();
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Class<?> getReturnType() {
        return this.function().result().getClass();
    }
}
