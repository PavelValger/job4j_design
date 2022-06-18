package ru.job4j.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;

public class Search {

    private static void validation(String[] args) {
        if (args.length < 2) {
            throw new IllegalArgumentException(
                    "Root folder or file extension is null."
                            + "Usage java -jar searchForInternalFolders.jar");
        }
        var file = Paths.get(args[0]).toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("Not exist %s", file.getAbsolutePath()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Not directory %s", file.getAbsolutePath()));
        }
        if (!args[1].startsWith(".")) {
            throw new IllegalArgumentException(
                    "The file extension must start with the character \".\"");
        }
    }

    public static void main(String[] args) {
        validation(args);
        Path start = Paths.get(args[0]);
        search(start, p -> p.toFile().getName().endsWith(args[1]))
                .forEach((path -> System.out.println(path.getFileName())));
    }

    public static List<Path> search(Path root, Predicate<Path> condition) {
        SearchFiles searcher = new SearchFiles(condition);
        try {
            Files.walkFileTree(root, searcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return searcher.getPaths();
    }
}
