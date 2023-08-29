package ru.netology.client.service.interfaces;

import java.io.IOException;

public interface MessageReader {
    String readLine() throws IOException;
}
