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
                    return u;
                }
            }
        }
        return null;
    }
}
