package ru.job4j.io;

import java.io.File;
import java.util.Arrays;
import java.util.Objects;

import static java.lang.System.out;

public class Dir {
    public static void main(String[] args) {
        File file = new File("c:\\projects");
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("Not exist %s", file.getAbsoluteFile()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Not directory %s", file.getAbsoluteFile()));
        }
        Arrays.stream(Objects.requireNonNull(file.listFiles()))
                .forEach((subFile) -> out.printf("file name: %s, file size: %.4f MB%n",
                subFile.getName(), (double) subFile.length() / 1048576));
    }
}
