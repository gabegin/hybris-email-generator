package com.github.gabegin.hybris.tools.emailGenerator;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Email;
import com.github.gabegin.hybris.tools.emailGenerator.extension.instrospection.ModelUberspector;
import com.github.gabegin.hybris.tools.emailGenerator.factory.ContextFactory;
import com.github.gabegin.hybris.tools.emailGenerator.writer.FileOutputWriter;
import com.github.gabegin.hybris.tools.emailGenerator.writer.OutputWriter;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;

import java.io.StringWriter;
import java.nio.file.Path;
import java.util.List;

public record EmailGenerator(List<Email> emails, Configurator configurator) {
    public void generate() {
        this.emails().forEach(this::generateEmail);
    }

    private void generateEmail(final Email email) {
        final VelocityEngine velocityEngine = this.initializeVelocityEngine(email);
        final Context context = ContextFactory.create(email.model(), email.resourceBundle());
        final StringWriter stringWriter = new StringWriter();

        velocityEngine.evaluate(context, stringWriter, email.template().getFilename(), email.template().content());

        this.createOutputWriter(email.outputFile()).write(stringWriter.toString());
    }

    private VelocityEngine initializeVelocityEngine(final Email email) {
        final VelocityEngine velocityEngine = new VelocityEngine();

        velocityEngine.setProperty("introspector.uberspect.class", ModelUberspector.class.getName());
        velocityEngine.setProperty("introspector.uberspect.model", email.model());
        velocityEngine.setProperty("resource.loader.file.path", email.template().path().getParent().toString());

        velocityEngine.init();

        return velocityEngine;
    }

    private OutputWriter createOutputWriter(final Path outputFile) {
        final Path outputPath = this.getOutputPath(outputFile);

        return new FileOutputWriter(outputPath);
    }

    private Path getOutputPath(final Path outputFile) {
        final Path outputDirectory = this.configurator().getOutputDirectory();

        if (outputFile.isAbsolute() || outputDirectory == null) {
            return outputFile;
        }

        return outputDirectory.resolve(outputFile);
    }
}
