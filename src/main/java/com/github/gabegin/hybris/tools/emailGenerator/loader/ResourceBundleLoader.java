package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configurator;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.ResourceBundle;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Template;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Sources;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;

import java.io.StringReader;
import java.nio.file.Path;

@Getter
@AllArgsConstructor
public final class ResourceBundleLoader implements AssetLoader<ResourceBundle> {
    private final Template template;
    private final Configurator configurator;

    @Override
    public Path getDirectory() {
        return Configuration.get().getResourceBundleDirectory();
    }

    @Override
    public String getExtension() {
        return ".properties";
    }

    @Override
    public String getName() {
        return Configuration.get().getResourceBundle();
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
    public ResourceBundle loadEmptyAsset() {
        return new ResourceBundle(null);
    }

    @Override
    public String resolveEmptyName() {
        final String messageSource = Sources.resolve(
            this.getTemplate().getPath(),
            this.getTemplate().getSource("messageSource"),
            this.getConfigurator().getLanguage()
        );

        if (messageSource != null) {
            return messageSource;
        }

        return this.getTemplate().getName();
    }
}
