package ru.netology.client.service;

import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.netology.client.model.Message;
import ru.netology.client.service.interfaces.MessageReader;
import ru.netology.client.service.interfaces.MessageWriter;
import ru.netology.client.service.interfaces.SocketWrapper;

import java.io.IOException;
import java.net.SocketException;

import static org.mockito.Mockito.*;

public class ChatClientTest {

    @Mock
    private SocketWrapper socketWrapper;

    @Mock
    private MessageReader messageReader;

    @Mock
    private MessageWriter messageWriter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        BasicConfigurator.configure();
    }

    @Test
    public void testSendMessage() throws IOException {
        //arrange
        String username = "testUser";
        ChatClient chatClient = new ChatClient(username, socketWrapper, messageReader, messageWriter);
        String text = "Hello, world!";
        Message message = new Message(username, text);
        String jsonMessage = message.toJson();
        //act
        boolean result = chatClient.sendMessage(text);
        //assert
        verify(messageWriter, times(1)).println(jsonMessage);
        verify(socketWrapper, never()).close();
        assert(result);
    }


    @Test
    public void testListenServerSocketException() throws IOException {
        //arrange
        ChatClient chatClient = new ChatClient("testUser", socketWrapper, messageReader, messageWriter);
        //act
        when(messageReader.readLine()).thenThrow(new SocketException());
        Thread thread = chatClient.listenServer();
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //assert
        verify(messageReader, times(1)).readLine();
        verify(socketWrapper, never()).close();
    }
}
