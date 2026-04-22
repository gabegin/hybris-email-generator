package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configurator;
import com.github.gabegin.hybris.tools.emailGenerator.entity.ResourceBundle;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Template;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Sources;
import lombok.SneakyThrows;

import java.io.StringReader;
import java.nio.file.Path;

public record ResourceBundleLoader(Template template, Configurator configurator) implements EntityLoader<ResourceBundle> {
    @Override
    public Path getDirectory() {
        return this.configurator().getResourceBundleDirectory();
    }

    @Override
    public String getExtension() {
        return ".properties";
    }

    @Override
    public String getName() {
        return this.configurator().getResourceBundle();
    }

    @Override
    public Class<ResourceBundle> getType() {
        return ResourceBundle.class;
    }

    @Override
    @SneakyThrows
    public ResourceBundle load(final Path path, final String content) {
        final StringReader stringReader = new StringReader(content);
        final ResourceBundle resourceBundle = new ResourceBundle(path);

        resourceBundle.load(stringReader);

        return resourceBundle;
    }

    @Override
    public ResourceBundle loadEmptyEntity() {
        return new ResourceBundle(null);
    }

    @Override
    public String resolveEmptyName() {
        final String messageSource = Sources.resolve(
            this.template().path(),
            this.template().getSource("messageSource"),
            this.configurator().getLanguage()
        );

        if (messageSource != null) {
            return messageSource;
        }

        return this.template().getName();
    }
}
