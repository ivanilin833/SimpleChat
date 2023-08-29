package ru.netology.client.service;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final String PROPERTIES_FILE = "client.properties";
    private static final Logger logger = Logger.getLogger(Config.class);
    public static int SERVER_PORT;
    public static String BYE_MESSAGE;
    public static String SERVER_IP;

    static {
        Properties properties = new Properties();
        InputStream propertiesStream = Config.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE);

        if (propertiesStream != null) {
            try {
                properties.load(propertiesStream);
                SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));
                BYE_MESSAGE = properties.getProperty("BYE_MESSAGE");
                SERVER_IP = properties.getProperty("SERVER_IP");
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

