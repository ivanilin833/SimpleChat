import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import ru.netology.client.service.*;
import ru.netology.client.service.interfaces.MessageReader;
import ru.netology.client.service.interfaces.MessageWriter;
import ru.netology.client.service.interfaces.SocketWrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientApp {
    private static final Logger logger = Logger.getLogger(ClientApp.class);

    public static void main(String[] args) {
        DOMConfigurator.configure("ClientApp/src/main/java/logConfiguration.xml");
        try {
            runClient();
        } catch (IOException e) {
            logger.error("Server disconnect " + e.getMessage());
        }
    }

    public static void runClient() throws IOException {
        Socket socket = new Socket(Config.SERVER_IP, Config.SERVER_PORT);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Введите ваше имя: ");
        String username = reader.readLine();

        SocketWrapper socketWrapper = new DefaultSocketWrapper(socket);
        MessageReader serverReader = new DefaultMessageReader(socket.getInputStream());
        MessageWriter writer = new DefaultMessageWriter(socket.getOutputStream());

        ChatClient client = new ChatClient(username, socketWrapper, serverReader, writer);
        client.listenServer().start();
        interactWithUser(reader, client);
    }

    public static void interactWithUser(BufferedReader reader, ChatClient client) throws IOException {
        boolean flag = true;
        while (flag) {
            String inputMessage = reader.readLine();
            flag = client.sendMessage(inputMessage);
        }
    }
}
