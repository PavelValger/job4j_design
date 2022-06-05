package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.Map;

public class DuplicatesVisitor extends SimpleFileVisitor<Path> {
    private final Map<Path, FileProperty> originalMap = new HashMap<>();
    private final Map<Path, FileProperty> copyMap = new HashMap<>();

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path path = file.toAbsolutePath();
        if (path.isAbsolute()) {
            FileProperty fileProperty
                    = new FileProperty(file.toFile().length(), file.toFile().getName());
            originalMap.put(path, fileProperty);
        }
        copyMap.putAll(originalMap);
        return super.visitFile(file, attrs);
    }

    public Map<Path, FileProperty> getOriginalMap() {
        return originalMap;
    }

    public Map<Path, FileProperty> getCopyMap() {
        return copyMap;
    }
}
