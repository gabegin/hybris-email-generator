package com.github.gabegin.hybris.tools.emailGenerator.entity;

import java.nio.file.Path;

public record Email(Template template, Model model, ResourceBundle resourceBundle, Path outputFile) {}
