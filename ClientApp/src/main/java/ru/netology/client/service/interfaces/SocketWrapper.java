package ru.netology.client.service.interfaces;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface SocketWrapper {
    InputStream getInputStream() throws IOException;

    OutputStream getOutputStream() throws IOException;

    void close() throws IOException;
}