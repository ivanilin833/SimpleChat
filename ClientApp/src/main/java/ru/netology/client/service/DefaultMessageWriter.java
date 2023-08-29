package ru.netology.client.service;

import ru.netology.client.service.interfaces.MessageWriter;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class DefaultMessageWriter implements MessageWriter {
    private final PrintWriter writer;

    public DefaultMessageWriter(OutputStream outputStream) {
        this.writer = new PrintWriter(new OutputStreamWriter(outputStream), true);
    }

    @Override
    public void println(String message) {
        writer.println(message);
    }
}