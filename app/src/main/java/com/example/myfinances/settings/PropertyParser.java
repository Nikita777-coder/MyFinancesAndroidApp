package com.example.myfinances.settings;

import java.io.IOException;
import java.util.Properties;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PropertyParser {
    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(Object.class.getResourceAsStream("config.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getValue(String key) {
        return properties.getProperty(key);
    }
}
