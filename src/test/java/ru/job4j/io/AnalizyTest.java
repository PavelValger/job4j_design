package ru.job4j.io;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import java.io.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class AnalizyTest {

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void whenServerWasNotWorking() throws IOException {
        Analizy analizy = new Analizy();
        File source = folder.newFile("source.txt");
        File target = folder.newFile("target.txt");
        try (PrintWriter record = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(source)))) {
            record.print("200 10:56:01\n"
                    + "500 10:57:01\n"
                    + "400 10:58:01\n"
                    + "200 10:59:01\n"
                    + "500 11:01:02\n"
                    + "200 11:02:02");
        }
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(target))) {
            read.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString(), is("10:57:01;10:59:01;"
                + "11:01:02;11:02:02;"));
    }

    @Test
    public void whenServerWorking() throws IOException {
        Analizy analizy = new Analizy();
        File source = folder.newFile("source.txt");
        File target = folder.newFile("target.txt");
        try (PrintWriter record = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(source)))) {
            record.print("200 10:56:01\n"
                    + "200 10:59:01\n"
                    + "200 11:02:02");
        }
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(target))) {
            read.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString(), is(""));
    }

    @Test
    public void whenServerCrashed() throws IOException {
        Analizy analizy = new Analizy();
        File source = folder.newFile("source.txt");
        File target = folder.newFile("target.txt");
        try (PrintWriter record = new PrintWriter(
                new BufferedOutputStream(
                        new FileOutputStream(source)))) {
            record.print("200 10:56:01\n"
                    + "500 10:57:01\n"
                    + "400 10:58:01\n"
                    + "500 11:01:02");
        }
        analizy.unavailable(source.getAbsolutePath(), target.getAbsolutePath());
        StringBuilder rsl = new StringBuilder();
        try (BufferedReader read = new BufferedReader(new FileReader(target))) {
            read.lines().forEach(rsl::append);
        }
        assertThat(rsl.toString(), is("10:57:01-"));
    }
}