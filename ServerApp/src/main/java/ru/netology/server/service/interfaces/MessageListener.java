package ru.netology.server.service.interfaces;

import ru.netology.server.model.Message;

public interface MessageListener {
    void onMessageReceived(Message msg);
}
