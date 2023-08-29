package ru.netology.server.service;

import org.apache.log4j.Logger;
import ru.netology.server.model.Message;
import ru.netology.server.service.interfaces.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class ServerImpl implements Server {
    private static final Logger logger = Logger.getLogger(ServerImpl.class);
    final List<ClientHandler> clients = new ArrayList<>();
    private ServerSocket serverSocket;
    private Thread serverThread;

    @Override
    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            logger.info("Server started on port " + port);
            serverThread = Thread.currentThread();

            while (!serverThread.isInterrupted()) {
                Socket clientSocket = null;
                while (clientSocket == null) {
                    clientSocket = serverSocket.accept();
                }
                ClientHandler clientHandler = new ClientHandler(clientSocket, this::broadcastMessage,
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream())),
                        new PrintWriter(clientSocket.getOutputStream(), true));
                clients.add(clientHandler);
                clientHandler.sendStartMessage();
                new Thread(clientHandler).start();
            }
        } catch (SocketException e) {
        } catch (IOException e) {
            logger.error("Error while starting the server: " + e.getMessage());
        }

    }

    @Override
    public void stop() {
        try {
            for (ClientHandler clientHandler : clients) {
                clientHandler.close();
            }
            serverSocket.close();
            serverThread.interrupt();
            logger.info("Server stopped");
        } catch (IOException e) {
            logger.error("Error while stopping the server: " + e.getMessage());
        }
    }

    void broadcastMessage(Message msg) {
        for (ClientHandler clientHandler : clients) {
            clientHandler.sendMessage(msg);
        }
        MessageLogger.log(msg);
    }
}
