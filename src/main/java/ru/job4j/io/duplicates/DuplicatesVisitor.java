package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final Map<FileProperty, List<Path>> map = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path path = file.toAbsolutePath();
        if (path.isAbsolute()) {
            FileProperty fileProperty
                    = new FileProperty(file.toFile().length(), file.toFile().getName());
            var keySearch = map.putIfAbsent(fileProperty, new ArrayList<>());
            var value = map.get(fileProperty);
            if (value.isEmpty() || keySearch != null) {
                value.add(path);
            }
        }
        return super.visitFile(file, attrs);
    }

    public Map<FileProperty, List<Path>> getMap() {
        return map;
    }
}
