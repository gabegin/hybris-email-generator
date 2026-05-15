package com.github.gabegin.hybris.tools.emailGenerator.entity;

import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Asset;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Model;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.ResourceBundle;
import com.github.gabegin.hybris.tools.emailGenerator.entity.asset.Template;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.file.Path;
import java.util.List;

@Getter
@AllArgsConstructor
public final class Email {
    private final Template template;
    private final Model model;
    private final ResourceBundle resourceBundle;
    private final Path outputFile;

    public List<Asset> getAssets() {
        return List.of(this.getTemplate(), this.getModel(), this.getResourceBundle());
    }
}
