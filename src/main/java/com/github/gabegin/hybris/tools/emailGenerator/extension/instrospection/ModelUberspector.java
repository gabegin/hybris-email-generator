package com.github.gabegin.hybris.tools.emailGenerator.extension.instrospection;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Function;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Model;
import com.github.gabegin.hybris.tools.emailGenerator.parser.FunctionParser;
import com.github.gabegin.hybris.tools.emailGenerator.traversal.Accessor;
import org.apache.velocity.util.introspection.Info;
import org.apache.velocity.util.introspection.UberspectImpl;
import org.apache.velocity.util.introspection.VelMethod;

import java.util.Objects;

public class ModelUberspector extends UberspectImpl {
    @Override
    public VelMethod getMethod(final Object object, final String methodName, final Object[] arguments, final Info information) {
        final VelMethod method = super.getMethod(object, methodName, arguments, information);

        if (method != null) {
            return method;
        }

        final Function<?> function = this.getFunction(object, methodName, arguments);

        if (function == null) {
            return null;
        }

        return new ModelMethod<>(function, this.getModel());
    }

    public Function<?> getFunction(final Object object, final String name, final Object[] arguments) {
        final Accessor accessor = new Accessor(object);

        return accessor.keys().stream()
            .map((key) -> FunctionParser.parse(key, accessor.get(key)))
            .filter(Objects::nonNull)
            .filter((function) -> function.name().equals(name) && function.accepts(arguments))
            .findFirst()
            .orElse(null);
    }

    private Model getModel() {
        return (Model) this.rsvc.getProperty("introspector.uberspect.model");
    }
}
