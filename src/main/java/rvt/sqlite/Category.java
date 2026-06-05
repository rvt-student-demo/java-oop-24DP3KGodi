package rvt.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Category {

    private int id;
    private String name;

    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() { return id; }
    public String getName() { return name; }

    public static boolean add(String name) throws SQLException {
        String sql = "INSERT INTO categories (name) VALUES (?)";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setString(1, name.trim());
            return ps.executeUpdate() > 0;
        }
    }

    public static List<Category> getAll() throws SQLException {
        List<Category> list = new ArrayList<>();
        String sql = "SELECT id, name FROM categories ORDER BY id";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Category(rs.getInt("id"), rs.getString("name")));
            }
        }
        return list;
    }

    public static boolean exists(int id) throws SQLException {
        String sql = "SELECT 1 FROM categories WHERE id = ?";
        try (PreparedStatement ps = DatabaseConnection.getConnection().prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    @Override
    public String toString() {
        return id + " | " + name;
    }
}
