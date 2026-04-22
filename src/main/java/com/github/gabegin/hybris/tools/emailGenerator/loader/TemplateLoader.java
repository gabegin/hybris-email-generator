package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configurator;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Template;

import java.nio.file.Path;

public record TemplateLoader(String name, Configurator configurator) implements EntityLoader<Template> {
    @Override
    public Path getDirectory() {
        return this.configurator().getTemplateDirectory();
    }

    @Override
    public String getExtension() {
        return ".vm";
    }

    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public Class<Template> getType() {
        return Template.class;
    }

    @Override
    public Template load(final Path path, final String content) {
        return new Template(path, content);
    }
}
