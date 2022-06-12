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

    private void pattern(String[] divider, String key) {
        if (divider.length != 2 || key.isEmpty()
                || !divider[0].startsWith("-") || divider[1].isEmpty()) {
            throw new IllegalArgumentException("Incorrect parameter input.");
        }
    }

    private void parse(String[] args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Parameters not entered.");
        }
        for (String str : args) {
            String[] divider = str.split("=", 2);
            var key = divider[0].replace("-", "");
            pattern(divider, key);
            values.put(key, divider[1]);
        }
    }

    public static ArgsName of(String[] args) {
        ArgsName names = new ArgsName();
        names.parse(args);
        return names;
    }
}
