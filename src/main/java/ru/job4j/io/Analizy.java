package ru.job4j.io;

import java.io.*;

public class Analizy {

    public void unavailable(String source, String target) {
        try (PrintWriter record = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(target)
                ))) {
            boolean statusOff = false;
            String start = null;
            try (BufferedReader read = new BufferedReader(new FileReader(source))) {
                for (String line = read.readLine(); line != null; line = read.readLine()) {
                    String[] divider = line.split(" ", 2);
                    if (!statusOff && (line.startsWith("400") || line.startsWith("500"))) {
                        statusOff = true;
                        start = divider[1] + ";";
                    } else if (!(line.startsWith("400") || line.startsWith("500"))) {
                        statusOff = false;
                        if (start != null) {
                            record.printf("%s%n", start + divider[1] + ";");
                        }
                    }
                }
                if (statusOff) {
                    record.print(start.replace(";", "-"));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String recite(String target) {
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(target))) {
            for (String line = read.readLine(); line != null; line = read.readLine()) {
                rsl.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl.toString();
    }
}
