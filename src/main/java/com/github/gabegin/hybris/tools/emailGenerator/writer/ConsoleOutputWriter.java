package com.github.gabegin.hybris.tools.emailGenerator.writer;

public class ConsoleOutputWriter implements OutputWriter {
    @Override
    public void write(final String content) {
        System.out.println(content);
    }
}
