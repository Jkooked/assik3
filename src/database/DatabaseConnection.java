package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/duken_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "061223";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL,USER,PASSWORD);
        } catch (SQLException e) {
            System.out.println("DB connection failed");
            e.printStackTrace();
            return null;
        }
    }
}
    public static void close(Connection c) {
        if (c == null) return;
        try { c.close(); } catch (SQLException ignored) {}
    }
}
