package com.github.gabegin.hybris.tools.emailGenerator.factory;

import com.github.gabegin.hybris.tools.emailGenerator.Configuration;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Email;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Model;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.ResourceBundle;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Template;
import com.github.gabegin.hybris.tools.emailGenerator.loader.ModelLoader;
import com.github.gabegin.hybris.tools.emailGenerator.loader.ResourceBundleLoader;
import com.github.gabegin.hybris.tools.emailGenerator.loader.TemplateLoader;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.util.List;

@Getter
@AllArgsConstructor
public final class EmailFactory {
    public List<Email> createEmails() {
        return Configuration.get().getTemplatePairs().entrySet().stream()
            .map((entry) -> this.createEmail(entry.getKey(), entry.getValue()))
            .toList();
    }

    private Email createEmail(final String name, final Path outputFile) {
        final Template template = new TemplateLoader(name).load();
        final Model model = new ModelLoader(template).load();
        final ResourceBundle resourceBundle = new ResourceBundleLoader(template).load();

        return new Email(template, model, resourceBundle, outputFile);
    }
}
