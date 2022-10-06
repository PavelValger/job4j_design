package ru.job4j.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.StringJoiner;

public class TableEditor implements AutoCloseable {
    private Connection connection;
    private final Properties properties;

    public TableEditor(Properties properties) throws SQLException, ClassNotFoundException {
        this.properties = properties;
        initConnection();
    }

    private void initConnection() throws ClassNotFoundException, SQLException {
        Class.forName(getValue("hibernate.connection.driver_class"));
        String url = getValue("hibernate.connection.url");
        String login = getValue("hibernate.connection.username");
        String password = getValue("hibernate.connection.password");
        connection = DriverManager.getConnection(url, login, password);
    }

    private void execute(String sql) throws SQLException {
        try (Statement statement = connection.createStatement()) {
            statement.execute(sql);
        }
    }

    public void createTable(String tableName) throws SQLException {
        String sql = String.format(
                "create table if not exists %s;", tableName
        );
        execute(sql);
    }

    public void dropTable(String tableName) throws SQLException {
        String sql = String.format(
                "drop table %s;", tableName
        );
        execute(sql);
    }

    public void addColumn(String tableName, String columnName, String type) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s ADD COLUMN %s %s;", tableName, columnName, type
        );
        execute(sql);
    }

    public void dropColumn(String tableName, String columnName) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s DROP COLUMN %s;", tableName, columnName
        );
        execute(sql);
    }

    public void renameColumn(
            String tableName, String columnName, String newColumnName) throws SQLException {
        String sql = String.format(
                "ALTER TABLE %s RENAME COLUMN %s to %s;", tableName, columnName, newColumnName
        );
        execute(sql);
    }

    public String getValue(String key) {
        return properties.getProperty(key);
    }

    public static String getTableScheme(Connection connection,
                                        String tableName) throws SQLException {
        var rowSeparator = "-".repeat(30).concat(System.lineSeparator());
        var header = String.format("%-15s|%-15s%n", "NAME", "TYPE");
        var buffer = new StringJoiner(rowSeparator, rowSeparator, rowSeparator);
        buffer.add(header);
        try (var statement = connection.createStatement()) {
            var selection = statement.executeQuery(String.format(
                    "select * from %s limit 1", tableName
            ));
            var metaData = selection.getMetaData();
            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                buffer.add(String.format("%-15s|%-15s%n",
                        metaData.getColumnName(i), metaData.getColumnTypeName(i))
                );
            }
        }
        return buffer.toString();
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        TableEditor tableEditor = new TableEditor(new ClassLoader().getProperties());
        tableEditor.createTable("demo_table()");
        var connect = tableEditor.connection;
        System.out.println(getTableScheme(connect, "demo_table"));
        tableEditor.addColumn("demo_table", "games", "text");
        System.out.println(getTableScheme(connect, "demo_table"));
        tableEditor.renameColumn("demo_table", "games", "game");
        System.out.println(getTableScheme(connect, "demo_table"));
        tableEditor.dropColumn("demo_table", "game");
        System.out.println(getTableScheme(connect, "demo_table"));
        tableEditor.dropTable("demo_table");
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}
