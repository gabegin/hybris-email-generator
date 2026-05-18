package com.github.gabegin.hybris.tools.emailGenerator.loader;

import com.github.gabegin.hybris.tools.emailGenerator.Configuration;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Model;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Template;
import com.github.gabegin.hybris.tools.emailGenerator.utility.Sources;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public final class ModelLoader implements AssetLoader<Model> {
    private final Template template;

    @Override
    public Model create(final Path path, final String content) {
        final Map<String, Object> attributes = new Gson().fromJson(content, new TypeToken<>() {}.getType());

        return new Model(path, attributes);
    }

    @Override
    public Model createEmptyAsset() {
        return new Model(null, new HashMap<>());
    }

    @Override
    public Path getDirectory() {
        return Configuration.get().getModelDirectory();
    }

    @Override
    public String getExtension() {
        return ".json";
    }

    @Override
    public String getName() {
        return Configuration.get().getModel();
    }

    @Override
    public Class<Model> getType() {
        return Model.class;
    }

    @Override
    public String resolveMissingName() {
        final String modelSource = Sources.resolve(this.getTemplate().getPath(), this.getTemplate().getSource("modelSource"));

        if (modelSource != null) {
            return modelSource;
        }

        return this.getTemplate().getName();
    }
}
