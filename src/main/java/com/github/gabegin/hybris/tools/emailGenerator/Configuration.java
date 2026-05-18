package com.github.gabegin.hybris.tools.emailGenerator;

import com.github.gabegin.hybris.tools.emailGenerator.extension.typeConverter.LanguageConverter;
import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.nio.file.Path;
import java.util.Locale;
import java.util.Map;

@Getter
@Setter
@Command(name = "Hybris Email Generator", version = "1.0.0")
public class Configuration {
    private static Configuration INSTANCE;

    @Option(
        names = { "-M", "--model-directory" },
        description = "Default lookup directory for models"
    )
    private Path modelDirectory;

    @Option(
        names = { "-R", "--resource-bundle-directory" },
        description = "Default lookup directory for resource bundles"
    )
    private Path resourceBundleDirectory;

    @Option(
        names = { "-O", "--output-directory" },
        description = "Default directory for outputting generated templates"
    )
    private Path outputDirectory;

    @Option(
        names = { "-T", "--template-directory" },
        defaultValue = "${HYBRIS_BIN_DIR}/custom/TWC/TWCcore/resources/TWCcore/import/emails",
        description = "Default lookup directory for templates"
    )
    private Path templateDirectory;

    @Option(
        names = { "-m", "--model" },
        description = "Path to model file used for all templates",
        paramLabel = "<model>.json"
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
        paramLabel = "<resourceBundle>.properties"
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

    public static Configuration get() {
        return INSTANCE;
    }

    public static Configuration initialize(final String... arguments) {
        INSTANCE = new Configuration();

        try {
            CommandLine.populateCommand(INSTANCE, arguments);
        } catch (final CommandLine.ParameterException exception) {
            System.err.println(exception.getMessage());
            CommandLine.usage(INSTANCE, System.err);
            System.exit(1);
        }

        return INSTANCE;
    }
}
