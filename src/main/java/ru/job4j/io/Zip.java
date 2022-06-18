package ru.job4j.io;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private static void validation(ArgsName value) {
        var file = Paths.get(value.get("d")).toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("The directory does not exist %s.", file.getAbsolutePath()));
        }
        if (!file.isDirectory()) {
            throw new IllegalArgumentException(
                    String.format("Is not a directory %s.", file.getAbsolutePath()));
        }
        if (!value.get("e").startsWith(".")) {
            throw new IllegalArgumentException("The file extension must start with a dot.");
        }
        if (!value.get("o").endsWith(".zip")) {
            throw new IllegalArgumentException(
                    "The archived directory must have the extension \".zip\".");
        }
    }

    public void packFiles(List<Path> sources, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(target)))) {
            for (Path path : sources) {
                zip.putNextEntry(new ZipEntry(path.toString()));
                try (BufferedInputStream out = new BufferedInputStream(
                        new FileInputStream(path.toFile()))) {
                    zip.write(out.readAllBytes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void packSingleFile(File source, File target) {
        try (ZipOutputStream zip = new ZipOutputStream(
                new BufferedOutputStream(new FileOutputStream(target)));
             BufferedInputStream out = new BufferedInputStream(
                     new FileInputStream(source))) {
            zip.putNextEntry(new ZipEntry(source.getPath()));
            zip.write(out.readAllBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ArgsName divider = ArgsName.of(args);
        validation(divider);
        List<Path> filter = Search.search(Path.of(divider.get("d")),
                p -> !p.toFile().getName().endsWith(divider.get("e")));
        Zip zip = new Zip();
        zip.packFiles(filter, new File(divider.get("o")));
        zip.packSingleFile(
                new File("./pom.xml"),
                new File("./pom.zip")
        );
    }
}
