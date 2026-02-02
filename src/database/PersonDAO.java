package database;

import model.Customer;
import exception.InvalidDiscountException;
import model.Person;
import model.VIPCustomer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO {

    // CREATE
    public boolean insertCustomer(Customer c) {
        String sql = "INSERT INTO people (person_type, name, discount) VALUES ('CUSTOMER', ?, 0)";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getName());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public boolean insertVIP(VIPCustomer v) {
        String sql = "INSERT INTO people (person_type, name, discount) VALUES ('VIP', ?, ?)";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, v.getName());
            st.setDouble(2, v.getDiscount());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    // READ
    public List<Person> getAllPeople() {
        List<Person> res = new ArrayList<>();
        String sql = "SELECT * FROM people ORDER BY id";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Person p = mapPerson(rs);
                if (p != null) res.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }

    public Person getById(int id) {
        String sql = "SELECT * FROM people WHERE id = ?";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return null;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) return mapPerson(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return null;
    }

    // UPDATE
    public boolean updateCustomer(Customer c) {
        // Для обычного customer: меняем только name, discount = 0, тип = CUSTOMER
        String sql = "UPDATE people SET person_type='CUSTOMER', name=?, discount=0 WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, c.getName());
            st.setInt(2, c.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    public boolean updateVIP(VIPCustomer v) {
        String sql = "UPDATE people SET person_type='VIP', name=?, discount=? WHERE id=?";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return false;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, v.getName());
            st.setDouble(2, v.getDiscount());
            st.setInt(3, v.getId());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DatabaseConnection.close(conn);
        }
    }

    // DELETE (Safe delete будет в меню)
    public boolean deletePerson(int id) {
        String sql = "DELETE FROM people WHERE id = ?";
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

    // SEARCH: by name (ILIKE %...%)
    public List<Person> searchByName(String namePart) {
        List<Person> res = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE name ILIKE ? ORDER BY id";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, "%" + namePart + "%");
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Person p = mapPerson(rs);
                    if (p != null) res.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }

    // SEARCH: discount min (только VIP)
    public List<Person> searchVIPMinDiscount(double minDiscount) {
        List<Person> res = new ArrayList<>();
        String sql = "SELECT * FROM people WHERE person_type='VIP' AND discount >= ? ORDER BY discount DESC";
        Connection conn = DatabaseConnection.getConnection();
        if (conn == null) return res;

        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setDouble(1, minDiscount);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    Person p = mapPerson(rs);
                    if (p != null) res.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DatabaseConnection.close(conn);
        }
        return res;
    }

    private Person mapPerson(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String type = rs.getString("person_type");
        String name = rs.getString("name");
        double discount = rs.getDouble("discount");

        try {
            if ("VIP".equalsIgnoreCase(type)) return new VIPCustomer(id, name, discount);
            return new Customer(id, name);
        } catch (InvalidDiscountException e) {
            // если в БД кривые данные
            System.out.println("Invalid data in DB: " + e.getMessage());
            return null;
        }
    }
}
