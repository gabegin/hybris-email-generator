package com.github.gabegin.hybris.tools.emailGenerator.writer;

public class ConsoleWriter implements Writer {
    @Override
    public void write(final String content) {
        System.out.println(content);
    }
}
