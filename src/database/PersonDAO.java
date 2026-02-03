package database;

import exception.InvalidDiscountException;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    public boolean insertCustomer(Customer c) {
        String sql = "INSERT INTO people (person_type, name, discount) VALUES ('CUSTOMER', ?, 0)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getName());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public boolean insertVIP(VIPCustomer v) {
        String sql = "INSERT INTO people (person_type, name, discount) VALUES ('VIP', ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, v.getName());
            st.setDouble(2, v.getDiscount());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public List<Person> getAllPeople() {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM people ORDER BY id";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) list.add(map(rs));

        } catch (SQLException ignored) {
        } finally {
            DatabaseConnection.close(conn);
        }
        return list;
    }

    public Person getById(int id) {
        String sql = "SELECT * FROM people WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) return map(rs);
        } catch (SQLException ignored) {
        } finally {
            DatabaseConnection.close(conn);
        }
        return null;
    }

    public boolean updateCustomer(Customer c) {
        String sql = "UPDATE people SET name=?, discount=0 WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getName());
            st.setInt(2, c.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public boolean updateVIP(VIPCustomer v) {
        String sql = "UPDATE people SET name=?, discount=? WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, v.getName());
            st.setDouble(2, v.getDiscount());
            st.setInt(3, v.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public boolean deletePerson(int id) {
        String sql = "DELETE FROM people WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public List<Person> searchByName(String name) {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE name ILIKE ?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + name + "%");
            ResultSet rs = st.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ignored) {
        } finally {
            DatabaseConnection.close(conn);
        }
        return list;
    }

    public List<Person> searchVIPMinDiscount(double min) {
        List<Person> list = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE person_type='VIP' AND discount>=?";
        Connection conn = DatabaseConnection.getConnection();
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, min);
            ResultSet rs = st.executeQuery();
            while (rs.next()) list.add(map(rs));
        } catch (SQLException ignored) {
        } finally {
            DatabaseConnection.close(conn);
        }
        return list;
    }

    private Person map(ResultSet rs)
            throws SQLException {
        try {
            return rs.getString("person_type").equals("VIP")
                    ? new VIPCustomer(rs.getInt("id"),
                    rs.getString("name"),
                    rs.getDouble("discount"))
                    : new Customer(rs.getInt("id"),
                    rs.getString("name"));
        } catch (InvalidDiscountException e) {
            return null;
        }
    }
}
