package rvt.sqlite;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class tododb {
    private static final String DB_URL = "jdbc:sqlite:todo.db";

    public tododb() {
        initSchema();
    }

    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    private void initSchema() {
        String sql = "CREATE TABLE IF NOT EXISTS todo ("
                   + "id INTEGER PRIMARY KEY, "
                   + "task TEXT NOT NULL)";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Schema init failed: " + e.getMessage());
        }
    }

    public void add(String task) throws SQLException {
        String sql = "INSERT INTO todo (task) VALUES (?)";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, task);
            ps.executeUpdate();
        }
    }

    public List<String> findAll() throws SQLException {
        List<String> list = new ArrayList<>();
        String sql = "SELECT id, task FROM todo";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(rs.getInt("id") + ". " + rs.getString("task"));
            }
        }
        return list;
    }

    public boolean removeById(int id) throws SQLException {
        String sql = "DELETE FROM todo WHERE id = ?";
        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }
}