package ru.job4j.io.searcher;

import ru.job4j.io.ArgsName;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Scanner;

public class FileSearch {
    private static final Charset CODING = Charset.forName("Windows-1251");

    private static void record(LinkedList<Path> visitor, String log) {
        try (PrintWriter out = new PrintWriter(
                new BufferedWriter(
                        new FileWriter(log, CODING, false)))) {
            visitor.forEach(out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static LinkedList<Path> search(String root, String wanted, String typeSearch) {
        Visitor visitor = new Visitor(wanted, typeSearch);
        try {
            Files.walkFileTree(Paths.get(root), visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return visitor.getFoundling();
    }

    private static void validation(String root, String typeSearch, String log) {
        var directory = Paths.get(root);
        if (!Files.exists(directory)) {
            readTip();
            throw new IllegalArgumentException(
                    String.format("Directory not exist - \"%s\"", directory.toAbsolutePath()));
        }
        if (!Files.isDirectory(directory)) {
            readTip();
            throw new IllegalArgumentException(
                    String.format("Not directory - \"%s\"", directory.toAbsolutePath()));
        }
        if (!log.endsWith(".txt")) {
            readTip();
            throw new IllegalArgumentException(
                    "The file must have the extension \".txt\"");
        }
        if (!"mask".equals(typeSearch)
                && !"name".equals(typeSearch) && !"regex".equals(typeSearch)) {
            readTip();
            throw new IllegalArgumentException(
                    String.format("The search type \"%s\" is set incorrectly", typeSearch));
        }
    }

    private static void readTip() {
        try (var scanner = new Scanner(Path.of(
                "C:/projects/job4j_design/src/main/java/ru/job4j/io/searcher/tip.txt"))) {
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArgsName argsName = ArgsName.of(args);
        String root = argsName.get("d");
        var wanted = argsName.get("n");
        var typeSearch = argsName.get("t");
        var log = argsName.get("o");
        validation(root, typeSearch, log);
        LinkedList<Path> foundling = search(root, wanted, typeSearch);
        record(foundling, log);
    }
}
