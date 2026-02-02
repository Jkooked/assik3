package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // поменяй под себя
    private static final String URL = "jdbc:postgresql://localhost:5432/restaurant_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB connection failed");
            e.printStackTrace();
            return null;
        }
    }

    public static void close(Connection c) {
        if (c == null) return;
        try { c.close(); } catch (SQLException ignored) {}
    }
}
