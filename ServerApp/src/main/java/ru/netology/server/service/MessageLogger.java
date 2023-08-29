package ru.netology.server.service;

import org.apache.log4j.Logger;
import ru.netology.server.model.Message;

public class MessageLogger {
    private static final Logger logger = Logger.getLogger(MessageLogger.class);

    public static void log(Message msg) {
        logger.info(msg.getUsername() + " : " + msg.getMessage() + " : " + msg.getDate());
    }
}
