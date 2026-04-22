package com.github.gabegin.hybris.tools.emailGenerator;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Email;
import com.github.gabegin.hybris.tools.emailGenerator.factory.EmailFactory;
import picocli.CommandLine;
import picocli.CommandLine.ParameterException;

import java.util.List;

public class CLI {
    public static void main(final String[] arguments) {
        final Configurator configurator = getConfiguration(arguments);

        if (configurator.isHelp() || configurator.isVersion()) {
            showHelp(configurator);
        }

        final EmailFactory emailFactory = new EmailFactory(configurator);
        final List<Email> emails = emailFactory.createEmails();

        new EmailGenerator(emails, configurator).generate();
    }

    private static void showHelp(final Configurator configurator) {
        final CommandLine commandLine = new CommandLine(configurator);

        if (configurator.isHelp()) {
            commandLine.usage(System.out);
        } else {
            commandLine.printVersionHelp(System.out);
        }

        System.exit(0);
    }

    private static Configurator getConfiguration(final String[] arguments) {
        final Configurator configurator = new Configurator();

        try {
            CommandLine.populateCommand(configurator, arguments);
        } catch (final ParameterException exception) {
            System.err.println(exception.getMessage());
            CommandLine.usage(configurator, System.err);
            System.exit(1);
        }

        return configurator;
    }
}
