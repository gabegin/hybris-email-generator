package com.github.gabegin.hybris.tools.emailGenerator.factory;

import com.github.gabegin.hybris.tools.emailGenerator.Configurator;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Email;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Model;
import com.github.gabegin.hybris.tools.emailGenerator.entity.ResourceBundle;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Template;
import com.github.gabegin.hybris.tools.emailGenerator.loader.ModelLoader;
import com.github.gabegin.hybris.tools.emailGenerator.loader.ResourceBundleLoader;
import com.github.gabegin.hybris.tools.emailGenerator.loader.TemplateLoader;

import java.nio.file.Path;
import java.util.List;

public record EmailFactory(Configurator configurator) {
    public List<Email> createEmails() {
        return this.configurator().getTemplatePairs().entrySet().stream()
            .map((entry) -> this.createEmail(entry.getKey(), entry.getValue()))
            .toList();
    }

    private Email createEmail(final String name, final Path outputFile) {
        final TemplateLoader templateLoader = new TemplateLoader(name, this.configurator());
        final Template template = templateLoader.load();

        final ModelLoader modelLoader = new ModelLoader(template, configurator());
        final Model model = modelLoader.load();

        final ResourceBundleLoader resourceBundleLoader = new ResourceBundleLoader(template, configurator());
        final ResourceBundle resourceBundle = resourceBundleLoader.load();

        return new Email(template, model, resourceBundle, outputFile);
    }
}
