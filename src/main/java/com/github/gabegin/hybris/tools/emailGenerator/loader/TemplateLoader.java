package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configurator;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Template;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;

@Getter
@AllArgsConstructor
public final class TemplateLoader implements AssetLoader<Template> {
    private final String name;
    private final Configurator configurator;

    @Override
    public Path getDirectory() {
        return Configuration.get().getTemplateDirectory();
    }

    @Override
    public String getExtension() {
        return ".vm";
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
