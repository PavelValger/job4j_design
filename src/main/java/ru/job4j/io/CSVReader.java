package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CSVReader {
    private static final Charset CODING = StandardCharsets.UTF_8;
    private static final List<String> RSL = new LinkedList<>();
    private static final List<Integer> FILTERED = new LinkedList<>();

    private static void stringBuilder(String[] divider, String delimiter) {
        var stringBuilder = new StringBuilder();
        for (Integer index : FILTERED) {
            stringBuilder.append(String.format("%s" + delimiter, divider[index]));
        }
        if (stringBuilder.length() > 0) {
            RSL.add(stringBuilder.substring(0, stringBuilder.length() - 1));
        }
    }

    private static List<String> readSource(
            String path, String delimiter, String filter) {
        try (var scanner = new Scanner(Path.of(path))) {
            if (scanner.hasNextLine()) {
                String[] cell = scanner.nextLine().split(delimiter);
                String[] filterCell = filter.split(",");
                for (int i = 0; i < cell.length; i++) {
                    for (String point : filterCell) {
                        if (point.equals(cell[i])) {
                            FILTERED.add(i);
                        }
                    }
                }
                stringBuilder(cell, delimiter);
                while (scanner.hasNextLine()) {
                    String[] divider = scanner.nextLine().split(delimiter);
                    stringBuilder(divider, delimiter);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return RSL;
    }

    private static void saveTarget(String path, List<String> target) {
        try (PrintWriter out = new PrintWriter(
                new FileWriter(path, CODING, false))) {
            target.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validation(ArgsName value) {
        if (value.getValues().size() != 4) {
            throw new IllegalArgumentException(
                    "Incorrect number of input parameters");
        }
        File in = Paths.get(value.get("path")).toFile();
        File out = Paths.get(value.get("out")).toFile();
        if (!in.exists() || !identical(value) && !out.exists()) {
            throw new IllegalArgumentException(String.format(
                    "The file does not exist %s or %s.",
                    in.getAbsolutePath(), out.getAbsolutePath()));
        }
        if (!value.get("path").endsWith(".csv")
                || !identical(value) && !value.get("out").endsWith(".csv")) {
            throw new IllegalArgumentException(String.format(
                    "Files %s, %s must have the extension \".csv\".",
                    in.getName(), out.getName()));
        }
    }

    private static boolean identical(ArgsName argsName) {
        return argsName.get("out").equals("stdout");
    }

    public static void handle(ArgsName argsName) {
        validation(argsName);
        var read = readSource(argsName.get("path"),
                argsName.get("delimiter"), argsName.get("filter"));
        if (identical(argsName)) {
            read.forEach(System.out::println);
        } else {
            saveTarget(argsName.get("out"), read);
        }
    }

    public static void main(String[] args) throws IOException {
        handle(ArgsName.of(args));
    }
}
