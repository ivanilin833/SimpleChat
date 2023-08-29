package ru.netology.server.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.netology.server.model.Message;
import ru.netology.server.service.interfaces.MessageListener;

import java.io.*;
import java.net.Socket;

import static org.mockito.Mockito.*;

public class ClientHandlerTest {

    @Mock
    private Socket mockSocket;

    @Mock
    private MessageListener mockListener;

    @Mock
    private BufferedReader mockReader;

    @Mock
    private PrintWriter mockWriter;

    private ClientHandler clientHandler;

    @BeforeEach
    public void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        when(mockSocket.getInputStream()).thenReturn(mock(InputStream.class));
        when(mockSocket.getOutputStream()).thenReturn(mock(OutputStream.class));

        when(mockReader.readLine()).thenReturn("Test message").thenReturn(null);
        clientHandler = new ClientHandler(mockSocket, mockListener, mockReader, mockWriter);
    }

    @Test
    public void testSendStartMessage() throws IOException {
        //arrange
        Message receivedMessage = new Message("user", "Hello");
        //act
        when(mockReader.readLine()).thenReturn(receivedMessage.toJson());
        clientHandler.sendStartMessage();
        //assert
        verify(mockListener).onMessageReceived(any(Message.class));
    }

    @Test
    public void testRunWithStopMessage() throws IOException {
        //arrange
        Message stopMessage = new Message("user", Config.STOP_MESSAGE);
        //act
        when(mockReader.readLine()).thenReturn(stopMessage.toJson()).thenReturn(null);
        clientHandler.run();
        //assert
        verify(mockListener).onMessageReceived(any(Message.class));
    }

    @Test
    public void testSendMessage() {
        //arrange
        Message message = new Message("server", "Test message");
        //act
        clientHandler.sendMessage(message);
        //assert
        verify(mockWriter).println(message.toJson());
    }

    @Test
    public void testClose() throws IOException {
        //act
        clientHandler.close();
        //assert
        verify(mockSocket).close();
        verify(mockReader).close();
        verify(mockWriter).close();
    }
}
