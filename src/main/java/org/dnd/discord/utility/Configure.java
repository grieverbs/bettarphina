package org.dnd.discord.utility;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Configure {
    final static private String CONFIG_PROPERTIES_NAME = "config.properties";
    final static private String BOT_TOKEN = "botToken";

    public String getBotToken() throws IOException {
        final var inputStream = getClass().getClassLoader().getResourceAsStream(CONFIG_PROPERTIES_NAME);
        var properties = new Properties();
        if(inputStream != null) {
            properties.load(inputStream);
        } else {
            final var errorMessage = String.format("property file '%s' not found in the classpath", CONFIG_PROPERTIES_NAME);
            throw new FileNotFoundException(errorMessage);
        }
        return properties.getProperty(BOT_TOKEN);
    }
}
