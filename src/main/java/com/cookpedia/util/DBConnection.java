package com.cookpedia.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://cookpedia-db.mysql.database.azure.com:3306/cookpedia_db?useSSL=true&serverTimezone=UTC";
    private static final String USER = "cookpedia_admin";
    private static final String PASSWORD = "HidupJokowi!";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new SQLException("MySQL Driver not found", e);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
