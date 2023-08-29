package ru.netology.client.service;

import ru.netology.client.service.interfaces.MessageReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DefaultMessageReader implements MessageReader {
    private final BufferedReader reader;

    public DefaultMessageReader(InputStream inputStream) {
        this.reader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public String readLine() throws IOException {
        return reader.readLine();
    }
}