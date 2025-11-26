package com.dao;

import com.model.User;
import java.sql.*;

public class UserDAO {
    private Connection conn;

    public UserDAO(Connection conn) {
        this.conn = conn;
    }

    public User findByCredentials(String username, String password) throws SQLException {
        String sql = "SELECT * FROM users WHERE username=? AND password_hash=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User u = new User();
                    u.setId(rs.getInt("id"));
                    u.setUsername(rs.getString("username"));
                    u.setFullName(rs.getString("full_name"));
                    u.setRoleId(rs.getInt("role_id"));
                    u.setEmployeeId(rs.getInt("employee_id"));
                    // try to read first_connexion if present
                    try {
                        java.sql.ResultSetMetaData md = rs.getMetaData();
                        boolean has = false;
                        for (int i = 1; i <= md.getColumnCount(); i++) {
                            if ("first_connexion".equalsIgnoreCase(md.getColumnName(i))) { has = true; break; }
                        }
                        if (has) {
                            u.setFirstConnexion(rs.getBoolean("first_connexion"));
                        }
                    } catch (SQLException ignore) {
                        // ignore, column may not exist
                    }
                    return u;
                }
            }
        }
        return null;
    }

    public void create(User u) throws SQLException {
    // Try to insert with first_connexion column if present, otherwise fallback
    String sqlWithFlag = "INSERT INTO users (username, password_hash, full_name, role_id, first_connexion, active, employee_id) VALUES (?,?,?,?,?,?,?)";
        String sqlNoFlag = "INSERT INTO users (username, password_hash, full_name, role_id, active, employee_id) VALUES (?,?,?,?,?,?)";
        try {
            try (PreparedStatement ps = conn.prepareStatement(sqlWithFlag, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPasswordHash());
                ps.setString(3, u.getFullName());
                ps.setInt(4, u.getRoleId());
                ps.setBoolean(5, u.isFirstConnexion());
                ps.setBoolean(6, u.isActive());
                if (u.getEmployeeId() == null) ps.setNull(7, java.sql.Types.INTEGER); else ps.setInt(7, u.getEmployeeId());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) u.setId(keys.getInt(1)); }
            }
        } catch (SQLException ex) {
            // If the error is unknown column (no first_connexion), fallback to old insert
            try (PreparedStatement ps = conn.prepareStatement(sqlNoFlag, java.sql.Statement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, u.getUsername());
                ps.setString(2, u.getPasswordHash());
                ps.setString(3, u.getFullName());
                ps.setInt(4, u.getRoleId());
                ps.setBoolean(5, u.isActive());
                if (u.getEmployeeId() == null) ps.setNull(6, java.sql.Types.INTEGER); else ps.setInt(6, u.getEmployeeId());
                ps.executeUpdate();
                try (ResultSet keys = ps.getGeneratedKeys()) { if (keys.next()) u.setId(keys.getInt(1)); }
            }
        }
    }

    public void updatePassword(int userId, String newPassword) throws SQLException {
    // Try to clear first_connexion if column exists
    String sqlWithFlag = "UPDATE users SET password_hash = ?, first_connexion = 0 WHERE id = ?";
        String sqlNoFlag = "UPDATE users SET password_hash = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sqlWithFlag)) {
            ps.setString(1, newPassword);
            ps.setInt(2, userId);
            ps.executeUpdate();
        } catch (SQLException ex) {
            try (PreparedStatement ps = conn.prepareStatement(sqlNoFlag)) {
                ps.setString(1, newPassword);
                ps.setInt(2, userId);
                ps.executeUpdate();
            }
        }
    }
}
