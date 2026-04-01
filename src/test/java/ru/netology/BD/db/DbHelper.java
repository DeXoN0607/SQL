package ru.netology.BD.db;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;

public class DbHelper {

    private static final String URL = "jdbc:mysql://localhost:3306/app";
    private static final String USER = "app";
    private static final String PASS = "pass";

    private DbHelper() {}

    @SneakyThrows
    public static String getVerificationCode(String login) {
        QueryRunner runner = new QueryRunner();
        String sql =
                "SELECT code FROM auth_codes " +
                        "WHERE user_id = (SELECT id FROM users WHERE login = ?) " +
                        "ORDER BY created DESC LIMIT 1;";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        }
    }

    @SneakyThrows
    public static void cleanDatabase() {
        QueryRunner runner = new QueryRunner();
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            runner.update(conn, "DELETE FROM auth_codes;");
            runner.update(conn, "DELETE FROM users;");
        }
    }
}