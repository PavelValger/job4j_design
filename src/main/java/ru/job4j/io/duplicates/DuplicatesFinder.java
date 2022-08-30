package ru.job4j.io.duplicates;

import java.io.Console;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class DuplicatesFinder {

    private static void collector(DuplicatesVisitor visitor) {
        var map = visitor.getMap();
        if (!map.isEmpty()) {
            map.entrySet().stream().filter(k -> k.getValue().size() > 1)
                    .forEach(System.out::println);
        }
    }

    public static void main(String[] args) {
        DuplicatesVisitor visitor = new DuplicatesVisitor();
        System.out.println("Enter the search directory, for example \"D:\\Games\\\":");
        Console console = System.console();
        String directory;
        if (console != null) {
            directory = console.readLine();
        } else {
            var scanner = new Scanner(System.in);
            directory = scanner.nextLine();
        }
        Path path = Path.of(directory).toAbsolutePath();
        try {
            Files.walkFileTree(Paths.get(String.valueOf(path)),
                    visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        collector(visitor);
    }
}
