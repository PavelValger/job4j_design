package ru.job4j.io;

import java.util.HashMap;
import java.util.Map;

public class ArgsName {
    private final Map<String, String> values = new HashMap<>();

    public String get(String key) {
        if (!values.containsKey(key)) {
            throw new IllegalArgumentException("The key was not found.");
        }
        return values.get(key);
    }

    private void pattern(String string) {
        if (!string.matches("-.+=.+")) {
            throw new IllegalArgumentException("Incorrect parameter input.");
        }
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Parameters not entered.");
        }
        for (String string : args) {
            pattern(string);
            String[] divider = string.split("=", 2);
            values.put(divider[0].replace("-", ""), divider[1]);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}
