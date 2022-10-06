package ru.job4j.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassLoader {
    private final Properties properties = new Properties();

    public ClassLoader() {
        load();
    }

    private void load() {
        try (InputStream in = ClassLoader.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Properties getProperties() {
        return properties;
    }
}
