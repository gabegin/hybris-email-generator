package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configuration;
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

    @Override
    @SneakyThrows
    public ResourceBundle create(final Path path, final String content) {
        final StringReader stringReader = new StringReader(content);
        final ResourceBundle resourceBundle = new ResourceBundle(path);

        resourceBundle.load(stringReader);

        return resourceBundle;
    }

    @Override
    public ResourceBundle createEmptyAsset() {
        return new ResourceBundle(null);
    }

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
    public String resolveMissingName() {
        final String messageSource = Sources.resolve(this.getTemplate().getPath(), this.getTemplate().getSource("messageSource"));

        if (messageSource != null) {
            return messageSource;
        }

        return this.getTemplate().getName();
    }
}
