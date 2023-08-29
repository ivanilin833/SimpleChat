package ru.netology.server.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "server.properties";
    private static final Logger logger = Logger.getLogger(Config.class);
    public static int PORT;
    public static String BYE_MESSAGE;
    public static String HELLO_MESSAGE;
    public static String STOP_MESSAGE;

    static {
        Properties properties = new Properties();
        InputStream propertiesStream = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);

        if (propertiesStream != null) {
            try {
                properties.load(propertiesStream);
                PORT = Integer.parseInt(properties.getProperty("PORT"));
                BYE_MESSAGE = properties.getProperty("BYE_MESSAGE");
                HELLO_MESSAGE = properties.getProperty("HELLO_MESSAGE");
                STOP_MESSAGE = properties.getProperty("STOP_MESSAGE");
            } catch (IOException e) {
                logger.error("Error while reading properties file: " + e.getMessage());
            } finally {
                try {
                    propertiesStream.close();
                } catch (IOException e) {
                    logger.error("Error while closing properties file: " + e.getMessage());
                }
            }
        } else {
            logger.error("Properties config file not found");
        }
    }
}
