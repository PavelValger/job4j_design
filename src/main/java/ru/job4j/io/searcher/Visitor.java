package ru.job4j.io.searcher;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.LinkedList;

public class Visitor extends SimpleFileVisitor<Path> {
    private final String wanted;
    private final String typeSearch;
    private boolean mask = true;
    private boolean name = true;
    private boolean regex = true;
    private final LinkedList<Path> foundling = new LinkedList<>();

    public Visitor(String wanted, String typeSearch) {
        this.wanted = wanted;
        this.typeSearch = typeSearch;
    }

    private void mask(Path file) {
        var pattern = wanted
                .replace(".", "\\.")
                .replace("*", ".*")
                .replace("?", ".{1}");
        if (file.getFileName().toString().matches(pattern)) {
            foundling.add(file.toAbsolutePath());
        }
    }

    private void name(Path file) {
        if (file.getFileName().toString().equals(wanted)) {
            foundling.add(file.toAbsolutePath());
        }
    }

    private void regex(Path file) {
        if (file.getFileName().toString().matches(wanted)) {
            foundling.add(file.toAbsolutePath());
        }
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (mask && "mask".equals(typeSearch)) {
            name = false;
            regex = false;
            mask(file);
        }
        if (name && "name".equals(typeSearch)) {
            mask = false;
            regex = false;
            name(file);
        }
        if (regex && "regex".equals(typeSearch)) {
            mask = false;
            name = false;
            regex(file);
        }
        return super.visitFile(file, attrs);
    }

    public LinkedList<Path> getFoundling() {
        return foundling;
    }
}
