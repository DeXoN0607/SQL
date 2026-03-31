package ru.netology.BD.db;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {

    private static final String url = "jdbc:mysql://localhost:3306/app";
    private static final String user = "app";
    private static final String pass = "pass";

    public static String getVerificationCode(String login) {
        QueryRunner runner = new QueryRunner();

        String sql =
                "SELECT code FROM auth_codes " +
                        "WHERE user_id = ( " +
                        "  SELECT id FROM users WHERE login = ? " +
                        ") " +
                        "ORDER BY created DESC " +
                        "LIMIT 1;";

        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            return runner.query(conn, sql, new ScalarHandler<>(), login);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}