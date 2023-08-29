import org.apache.log4j.xml.DOMConfigurator;
import ru.netology.server.service.Config;
import ru.netology.server.service.ServerImpl;
import ru.netology.server.service.interfaces.Server;

import java.util.Scanner;

public class ServerApp {
    public static void main(String[] args) {
        DOMConfigurator.configure("ServerApp/src/main/java/logConfiguration.xml");
        Server server = new ServerImpl();

        Thread serverThread = new Thread(() -> {
            server.start(Config.PORT);
        });

        serverThread.start();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сервер запущен. Введите 'stop' для остановки сервера.");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("stop")) {
                server.stop();
                break;
            }
        }
        System.out.println("Сервер остановлен.");
    }
}
