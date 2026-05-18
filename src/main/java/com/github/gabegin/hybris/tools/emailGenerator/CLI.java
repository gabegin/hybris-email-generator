package com.github.gabegin.hybris.tools.emailGenerator;

import com.github.gabegin.hybris.tools.emailGenerator.entity.Email;
import com.github.gabegin.hybris.tools.emailGenerator.factory.EmailFactory;
import picocli.CommandLine;

import java.util.List;

public class CLI {
    public static void main(final String[] arguments) {
        final Configuration configuration = Configuration.initialize(arguments);

        if (configuration.isHelp() || configuration.isVersion()) {
            showHelp();
        }

        final EmailFactory emailFactory = new EmailFactory();
        final List<Email> emails = emailFactory.createEmails();

        new EmailGenerator(emails).generate();
    }

    private static void showHelp() {
        final Configuration configuration = Configuration.get();
        final CommandLine commandLine = new CommandLine(configuration);

        if (configuration.isHelp()) {
            commandLine.usage(System.out);
        } else {
            commandLine.printVersionHelp(System.out);
        }

        System.exit(0);
    }
}
