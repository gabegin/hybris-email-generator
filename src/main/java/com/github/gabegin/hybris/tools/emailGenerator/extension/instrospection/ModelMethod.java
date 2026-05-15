package com.github.gabegin.hybris.tools.emailGenerator.extension.instrospection;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Function;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Model;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Templates;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.velocity.context.Context;
import org.apache.velocity.util.introspection.VelMethod;

import java.lang.reflect.Method;

@Getter
@AllArgsConstructor
public final class ModelMethod<T> implements VelMethod {
    private final Function<T> function;
    private final Model model;

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public String getMethodName() {
        return this.getFunction().getName();
    }

    @Override
    public Class<?> getReturnType() {
        return this.getFunction().getResult().getClass();
    }

    @Override
    public Object invoke(final Object object, final Object... arguments) {
        final T result = this.getFunction().getResult();

        if (!(result instanceof final String template)) {
            return result;
        }

        if (object instanceof Context) {
            return Templates.apply(template, this, this.getModel(), arguments);
        }

        return Templates.apply(template, this, object, arguments);
    }

    @Override
    public boolean isCacheable() {
        return false;
    }
}
