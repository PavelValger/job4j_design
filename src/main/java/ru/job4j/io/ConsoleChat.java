package ru.job4j.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ConsoleChat {
    private static final String OUT = "закончить";
    private static final String STOP = "стоп";
    private static final String CONTINUE = "продолжить";
    private static final Charset CODING = Charset.forName("UTF-8");
    private final String path;
    private final String botAnswers;

    public ConsoleChat(String path, String botAnswers) {
        this.path = path;
        this.botAnswers = botAnswers;
    }

    public void run() {
        List<String> log = new LinkedList<>();
        List<String> read = readPhrases();
        var scanner = new Scanner(System.in);
        addAnswer("-Задайте волнующий вас вопрос:", log);
        var botRun = true;
        while (botRun) {
            var question = scanner.nextLine();
            log.add(question);
            botRun = !question.equals(OUT);
            if (botRun) {
                if (STOP.equals(question)) {
                    addAnswer("-Бот остановлен!", log);
                    while (!CONTINUE.equals(question) && !OUT.equals(question)) {
                        question = scanner.nextLine();
                        log.add(question);
                    }
                    if (OUT.equals(question)) {
                        break;
                    }
                    addAnswer("-Бот продолжает работу! Задайте волнующий Вас вопрос:", log);
                    continue;
                }
                int random = new Random().nextInt(read.size());
                var answer = String.format("-%s", read.get(random));
                addAnswer(answer, log);
            }
        }
        addAnswer("-Бот завершил работу", log);
        saveLog(log);
    }

    private void addAnswer(String text, List<String> log) {
        System.out.println(text);
        log.add(text);
    }

    private List<String> readPhrases() {
        List<String> rsl = new ArrayList<>();
        try (BufferedReader in = new BufferedReader(new FileReader(botAnswers, CODING))) {
            for (String line = in.readLine(); line != null; line = in.readLine()) {
                if (!line.isEmpty()) {
                    rsl.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rsl;
    }

    private void saveLog(List<String> log) {
        try (PrintWriter out = new PrintWriter(
                new FileWriter(path, CODING, true))) {
            log.forEach(out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void validation(String path, String botAnswers) {
        var file = Paths.get(botAnswers).toFile();
        if (!file.exists()) {
            throw new IllegalArgumentException(
                    String.format("The file with the bot's responses was not found %s.",
                            Path.of(path).toAbsolutePath()));
        }
        if (!path.endsWith(".txt") || !botAnswers.endsWith(".txt")) {
            throw new IllegalArgumentException(
                    "The file must have the extension \".txt\".");
        }
    }

    public static void main(String[] args) throws IOException {
        ConsoleChat cc = new ConsoleChat("./data/dialog.txt", "./data/botAnswers.txt");
        validation(cc.path, cc.botAnswers);
        cc.run();
    }
}
