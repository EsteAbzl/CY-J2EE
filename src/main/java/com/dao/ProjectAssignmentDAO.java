package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProjectAssignmentDAO {
    private Connection conn;

    public ProjectAssignmentDAO(Connection conn) {
        this.conn = conn;
    }

    public void assignEmployeeToProject(int projectId, int employeeId, String role) throws SQLException {
        String sql = "INSERT INTO project_assignments (project_id, employee_id, role_in_project) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            ps.setString(3, role);
            ps.executeUpdate();
        }
    }

    public void removeEmployeeFromProject(int projectId, int employeeId) throws SQLException {
        String sql = "DELETE FROM project_assignments WHERE project_id=? AND employee_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            ps.executeUpdate();
        }
    }

    public List<Integer> getAssignedEmployeeIds(int projectId) throws SQLException {
        List<Integer> employeeIds = new ArrayList<>();
        String sql = "SELECT employee_id FROM project_assignments WHERE project_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    employeeIds.add(rs.getInt("employee_id"));
                }
            }
        }
        return employeeIds;
    }
}
