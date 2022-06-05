package ru.job4j.io.duplicates;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class DuplicatesFinder {
    public static void main(String[] args) {
        DuplicatesVisitor visitor = new DuplicatesVisitor();
        try {
            Files.walkFileTree(Paths.get("C:\\Users\\Павел\\Desktop\\music"),
                    visitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        var originalMap = visitor.getOriginalMap();
        if (!originalMap.isEmpty()) {
            for (Map.Entry<Path, FileProperty> entry : originalMap.entrySet()) {
                var key = entry.getKey();
                var value = entry.getValue();
                for (Map.Entry<Path, FileProperty> entryCopy : visitor.getCopyMap().entrySet()) {
                    var keyCopy = entryCopy.getKey();
                    var valueCopy = entryCopy.getValue();
                    if (value.equals(valueCopy) && !key.equals(keyCopy)) {
                        System.out.println(key);
                        break;
                    }
                }
            }
        }
    }
}
