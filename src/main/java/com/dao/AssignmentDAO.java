package com.dao;

import java.sql.*;
import java.util.*;

public class AssignmentDAO {
    private final Connection conn;
    public AssignmentDAO(Connection c){ this.conn = c; }

    public void assign(int projectId, int employeeId, String role) throws SQLException {
        String sql = "REPLACE INTO project_assignments(project_id, employee_id, role_in_project) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            ps.setString(3, role);
            ps.executeUpdate();
        }
    }

    public void unassign(int projectId, int employeeId) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM project_assignments WHERE project_id=? AND employee_id=?")) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            ps.executeUpdate();
        }
    }

    public List<Integer> employeesForProject(int projectId) throws SQLException {
        List<Integer> list = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement("SELECT employee_id FROM project_assignments WHERE project_id=?")) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) list.add(rs.getInt(1));
            }
        }
        return list;
    }
}
