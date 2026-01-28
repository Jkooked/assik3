import database.DatabaseConnection;
import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection c = DatabaseConnection.getConnection();
        System.out.println(c != null ? "CONNECTED ✅" : "FAILED ❌");
    }
}
