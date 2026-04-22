package com.github.gabegin.hybris.tools.emailGenerator.factory;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Model;
import com.github.gabegin.hybris.tools.emailGenerator.entity.ResourceBundle;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

public class ContextFactory {
    public static Context create(final Model model, final ResourceBundle resourceBundle) {
        final VelocityContext context = new VelocityContext();

        model.forEach(context::put);

        context.put("ctx", context);
        context.put("messages", resourceBundle);

        return context;
    }
}
