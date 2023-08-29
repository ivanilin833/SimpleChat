package ru.netology.client.service;

import org.apache.log4j.Logger;
import ru.netology.client.model.Message;
import ru.netology.client.service.interfaces.MessageReader;
import ru.netology.client.service.interfaces.MessageWriter;
import ru.netology.client.service.interfaces.SocketWrapper;

import java.io.IOException;
import java.net.SocketException;

public class ChatClient {
    private static final Logger logger = Logger.getLogger(ChatClient.class);
    private final String username;
    private final SocketWrapper socketWrapper;
    private final MessageReader serverReader;
    private final MessageWriter writer;

    public ChatClient(String username, SocketWrapper socketWrapper, MessageReader serverReader, MessageWriter writer) throws IOException {
        this.username = username;
        this.socketWrapper = socketWrapper;
        this.serverReader = serverReader;
        this.writer = writer;
        sendMessage("Start");
    }

    public Thread listenServer() {
        return new Thread(() -> {
            try {
                while (true) {
                    String jsonMessage = serverReader.readLine();
                    if (jsonMessage == null) {
                        break;
                    }
                    Message message = Message.fromJson(jsonMessage);
                    MessageLogger.log(message);
                    System.out.println(message.getUsername() + ": " + message.getMessage() + " (Time: " + message.getDate() + ")");
                }
            } catch (SocketException e) {
                logger.info(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        });
    }

    public boolean sendMessage(String text) throws IOException {
        if (text.equalsIgnoreCase("exit")) {
            Message message = new Message(username, Config.BYE_MESSAGE);
            String jsonMessage = message.toJson();
            writer.println(jsonMessage);
            socketWrapper.close();
            return false;
        }
        Message message = new Message(username, text);
        String jsonMessage = message.toJson();
        writer.println(jsonMessage);
        return true;
    }
}
