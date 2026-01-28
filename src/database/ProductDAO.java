package database;

import model.Product;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductDAO {

    public void insertProduct(Product product) {

        String sql =
                "INSERT INTO product (name, price, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setInt(3, product.getQuantity());

            ps.executeUpdate();
            System.out.println("Product inserted ✅");

        } catch (SQLException e) {
            System.out.println("Insert failed ❌");
            e.printStackTrace();
        }
    }

}
