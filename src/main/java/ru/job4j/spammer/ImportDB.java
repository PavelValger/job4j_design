package ru.job4j.spammer;

import java.io.*;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ImportDB {
    private final Properties cfg;
    private final String dump;

    public ImportDB(Properties cfg, String dump) {
        this.cfg = cfg;
        this.dump = dump;
    }

    public List<User> load() throws IOException {
        List<User> users = new ArrayList<>();
        try (var scanner = new Scanner(Path.of(dump)).useDelimiter(
                String.format(";%n"))) {
            while (scanner.hasNext()) {
                String[] divider = scanner.next().split(";", 2);
                User user = new User(divider[0], divider[1]);
                users.add(user);
            }
        }
        return users;
    }

    public void save(List<User> users) throws ClassNotFoundException, SQLException {
        Class.forName(cfg.getProperty("jdbc.driver"));
        try (Connection cnt = DriverManager.getConnection(
                cfg.getProperty("jdbc.url"),
                cfg.getProperty("jdbc.username"),
                cfg.getProperty("jdbc.password")
        )) {
            for (User user : users) {
                try (PreparedStatement ps = cnt.prepareStatement(
                        "insert into users(name, email) values (?, ?)")) {
                    ps.setString(1, user.name);
                    ps.setString(2, user.email);
                    ps.execute();
                }
            }
        }
    }

    private record User(String name, String email) {
    }

    public static void main(String[] args) {
        Properties cfg = new Properties();
        try (InputStream in = ImportDB.class.getClassLoader()
                .getResourceAsStream("app.properties")) {
            cfg.load(in);
            ImportDB db = new ImportDB(cfg, "./dump.txt");
            db.save(db.load());
        } catch (ClassNotFoundException | SQLException | IOException e) {
            e.printStackTrace();
        }
    }
}
