package com.github.gabegin.hybris.tools.emailGenerator;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Email;
import com.github.gabegin.hybris.tools.emailGenerator.extension.instrospection.ModelUberspector;
import com.github.gabegin.hybris.tools.emailGenerator.factory.ContextFactory;
import com.github.gabegin.hybris.tools.emailGenerator.writer.FileOutputWriter;
import com.github.gabegin.hybris.tools.emailGenerator.writer.OutputWriter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;

@Getter
@AllArgsConstructor
public final class EmailGenerator {
    private final List<Email> emails;
    private final Configurator configurator;

    public void generate() {
        this.getEmails().forEach(this::generateEmail);
    }

    private OutputWriter createOutputWriter(final Path outputFile) {
        final Path outputPath = this.getOutputPath(outputFile);

        return new FileOutputWriter(outputPath);
    }

    private void generateEmail(final Email email) {
        final VelocityEngine velocityEngine = this.initializeVelocityEngine(email);
        final Context context = ContextFactory.create(email.getModel(), email.getResourceBundle());
        final StringWriter stringWriter = new StringWriter();

        velocityEngine.evaluate(context, stringWriter, email.getTemplate().getFilename(), email.getTemplate().getContent());

        this.createOutputWriter(email.getOutputFile()).write(stringWriter.toString());
    }

    private Path getOutputPath(final Path outputFile) {
        final Path outputDirectory = this.getConfigurator().getOutputDirectory();

        if (outputFile.isAbsolute() || outputDirectory == null) {
            return outputFile;
        }

        return outputDirectory.resolve(outputFile);
    }

    private VelocityEngine initializeVelocityEngine(final Email email) {
        final VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.setProperty("introspector.uberspect.class", ModelUberspector.class.getName());
        velocityEngine.setProperty("introspector.uberspect.model", email.getModel());
        velocityEngine.setProperty("resource.loader.file.path", email.getTemplate().getPath().getParent().toString());

        velocityEngine.init();

        return velocityEngine;
    }
}
