package ru.netology.server.service;

import org.apache.log4j.Logger;
import ru.netology.server.model.Message;
import ru.netology.server.service.interfaces.MessageListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler implements Runnable {
    private static final Logger logger = Logger.getLogger(ClientHandler.class);
    private final BufferedReader reader;
    private final PrintWriter writer;
    private final MessageListener listener;
    private final Socket clientSocket;

    public ClientHandler(Socket clientSocket, MessageListener listener, BufferedReader reader, PrintWriter writer) {
        this.clientSocket = clientSocket;
        this.listener = listener;
        this.reader = reader;
        this.writer = writer;
    }

    public void sendStartMessage() throws IOException {
        Message msg = Message.fromJson(reader.readLine());
        listener.onMessageReceived(new Message("server", msg.getUsername() + " " + Config.HELLO_MESSAGE));
    }

    @Override
    public void run() {
        try {
            while (true) {
                String json = reader.readLine();
                if (json == null) {
                    break;
                }
                Message message = Message.fromJson(json);
                if (Objects.equals(message.getMessage(), Config.STOP_MESSAGE)) {
                    listener.onMessageReceived(new Message("server", message.getUsername() + " " + Config.BYE_MESSAGE));
                } else {
                    listener.onMessageReceived(message);
                }
            }
        } catch (IOException e) {
            close();
        }
    }

    public void sendMessage(Message msg) {
        String json = msg.toJson();
        writer.println(json);
    }

    public void close() {
        try {
            clientSocket.close();
            reader.close();
            writer.close();
        } catch (IOException e) {
            logger.error("Ошибка при закрытии сокета: " + e.getMessage());
        }
    }
}
