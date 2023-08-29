package ru.netology.client.service;

import org.apache.log4j.Logger;
import ru.netology.client.model.Message;

public class MessageLogger {
    private static final Logger logger = Logger.getLogger(MessageLogger.class);

    public static void log(Message msg) {
        logger.info(msg.getUsername() + " : " + msg.getMessage() + " : " + msg.getDate());
    }
}
