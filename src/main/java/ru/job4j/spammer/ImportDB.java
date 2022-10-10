package ru.job4j.spammer;

import java.io.*;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ImportDB {
    private static final Charset CODING = Charset.forName("UTF-8");
    private final Properties cfg;
    private final String dump;

    public ImportDB(Properties cfg, String dump) {
        this.cfg = cfg;
        this.dump = dump;
    }

    public List<User> load() {
        List<User> users = new ArrayList<>();
        try (BufferedReader read = new BufferedReader(new FileReader(dump, CODING))) {
            for (String line = read.readLine(); line != null; line = read.readLine()) {
                if (!line.startsWith("#") && !line.isEmpty()) {
                    String[] divider = line.split(";");
                    if (divider.length != 2 || divider[0].isEmpty() || divider[1].isEmpty()) {
                        throw new IllegalArgumentException(String.format(
                                "Attention! Invalid line in the document - \"%s\"", line)
                        );
                    }
                    User user = new User(divider[0], divider[1]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
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
