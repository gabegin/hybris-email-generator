package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configurator;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Model;
import com.github.gabegin.hybris.tools.emailGenerator.entity.Template;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Sources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public record ModelLoader(Template template, Configurator configurator) implements EntityLoader<Model> {
    @Override
    public Path getDirectory() {
        return this.configurator().getModelDirectory();
    }

    @Override
    public String getExtension() {
        return ".json";
    }

    @Override
    public String getName() {
        return this.configurator().getModel();
    }

    @Override
    public Class<Model> getType() {
        return Model.class;
    }

    @Override
    public Model load(final Path path, final String content) {
        final Map<String, Object> attributes = new Gson().fromJson(content, new TypeToken<>() {}.getType());

        return new Model(path, attributes);
    }

    @Override
    public Model loadEmptyEntity() {
        return new Model(null, new HashMap<>());
    }

    @Override
    public String resolveEmptyName() {
        final String modelSource = Sources.resolve(
            this.template().path(),
            this.template().getSource("modelSource"),
            this.configurator().getLanguage()
        );

        if (modelSource != null) {
            return modelSource;
        }

        return this.template().getName();
    }
}
