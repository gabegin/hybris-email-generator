package com.github.gabegin.hybris.tools.emailGenerator;

import com.github.gabegin.hybris.tools.emailGenerator.extension.typeConverter.LanguageConverter;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@Command(name = "Email Generator", version = "1.0.0")
public class Configurator {
    @Option(
        names = { "-M", "--model-directory" },
        description = "Default lookup directoy for models"
    )
    private Path modelDirectory;

    @Option(
        names = { "-R", "--resource-bundle-directory" },
        description = "Default lookup directoy for resource bundles"
    )
    private Path resourceBundleDirectory;

    @Option(
        names = { "-O", "--output-directory" },
        description = "Default directoy for outputting generated templates"
    )
    private Path outputDirectory;

    @Option(
        names = { "-T", "--template-directory" },
        defaultValue = "${HYBRIS_BIN_DIR}/custom/TWC/TWCcore/resources/TWCcore/import/emails",
        description = "Default lookup directoy for templates"
    )
    private Path templateDirectory;

    @Option(
        names = { "-m", "--model" },
        description = "Path to model file used for all templates"
    )
    private String model;

    @Option(
        names = { "-l", "--language" },
        converter = LanguageConverter.class,
        description = "Language used for looking up message sources"
    )
    private Locale language = Locale.getDefault();

    @Option(
        names = { "-r", "--resource-bundle" },
        description = "Path to message bundle file used for all templates",
        paramLabel = "<messageBundle>.properties"
    )
    private String resourceBundle;

    @Option(
        names = { "-h", "--help" },
        description = "Display this help guide",
        usageHelp = true
    )
    private boolean help;

    @Option(
        names = { "-v", "--version" },
        description = "Display current version",
        versionHelp = true
    )
    private boolean version;

    @Parameters(
        arity = "1..*",
        description = "Templates to generate",
        paramLabel = "<templateName>=<outputFile>"
    )
    private Map<String, Path> templatePairs;
}
