package rvt.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Product {

    private int id;
    private String name;
    private double price;
    private int categoryId;
    private String categoryName;

    public Product(int id, String name, double price, int categoryId, String categoryName) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    // Pievieno produktu
    public static boolean add(String name, double price, int categoryId) throws SQLException {
        String sql = "INSERT INTO products (name,  price,  category_id) VALUES (?, ?, ?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name.trim());
            ps.setDouble(2, price);
            ps.setInt(3, categoryId);
            return ps.executeUpdate() > 0;
        }
    }

    // Atgriez visus produktus ar kategorijas nosaukumu
    public static List<Product> getAll() throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.price, p.category_id, c.name AS cat_name " +
                     "FROM products p JOIN categories c ON p.category_id = c.id ORDER BY p.id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"), rs.getString("name"),
                    rs.getDouble("price"), rs.getInt("category_id"),
                    rs.getString("cat_name")));
            }
        }
        return list;
    }

    // Meklē produktus pēc kategorijas ID vai nosaukuma
    public static List<Product> searchByCategory(String input) throws SQLException {
        List<Product> list = new ArrayList<>();
        String sql;
        PreparedStatement ps;

        try {
            int id = Integer.parseInt(input);
            sql = "SELECT p.id, p.name, p.price, p.category_id, c.name AS cat_name " +
                  "FROM products p JOIN categories c ON p.category_id = c.id WHERE p.category_id = ?";
            ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
        } catch (NumberFormatException e) {
            sql = "SELECT p.id, p.name, p.price, p.category_id, c.name AS cat_name " +
                  "FROM products p JOIN categories c ON p.category_id = c.id " +
                  "WHERE LOWER(c.name) LIKE LOWER(?)";
            ps = DatabaseConnection.getConnection().prepareStatement(sql);
            ps.setString(1, "%" + input.trim() + "%");
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Product(
                    rs.getInt("id"), rs.getString("name"),
                    rs.getDouble("price"), rs.getInt("category_id"),
                    rs.getString("cat_name")));
            }
        }
        ps.close();
        return list;
    }

    @Override
    public String toString() {
        return id + " | " + name + " | " + price + " EUR | " + categoryName;
    }
}
