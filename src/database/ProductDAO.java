package database;

import model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    // CREATE
    public boolean insertProduct(Product p) {
        String sql = "INSERT INTO products (name, price) VALUES (?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    // READ
    public List<Product> getAllProducts() {
        List<Product> res = new ArrayList<>();
        String sql = "SELECT * FROM products ORDER BY id";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                res.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }

    public Product getById(int id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return null;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return null;
    }

    // UPDATE
    public boolean updateProduct(Product p) {
        String sql = "UPDATE products SET name=?, price=? WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, p.getName());
            st.setDouble(2, p.getPrice());
            st.setInt(3, p.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    // DELETE
    public boolean deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    // SEARCH: by name
    public List<Product> searchByName(String namePart) {
        List<Product> res = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE name ILIKE ? ORDER BY id";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + namePart + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    res.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }

    // SEARCH: price range
    public List<Product> searchByPriceRange(double min, double max) {
        List<Product> res = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE price BETWEEN ? AND ? ORDER BY price DESC";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, min);
            st.setDouble(2, max);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    res.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }

    // SEARCH: min price
    public List<Product> searchByMinPrice(double minPrice) {
        List<Product> res = new ArrayList<>();
        String sql = "SELECT * FROM products WHERE price >= ? ORDER BY price DESC";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, minPrice);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    res.add(new Product(rs.getInt("id"), rs.getString("name"), rs.getDouble("price")));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }
}
