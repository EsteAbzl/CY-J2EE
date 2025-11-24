package com.dao;

import java.sql.*;
import java.util.*;

public class ReportsDAO {
    private final Connection conn;

    public ReportsDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Retourne un mapping Département → Nombre d’employés
     */
    public Map<String, Integer> employeesByDepartment() throws SQLException {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT d.name AS dept_name, COUNT(e.id) AS total " +
                "FROM departments d " +
                "LEFT JOIN employees e ON e.department_id = d.id " +
                "GROUP BY d.name ORDER BY d.name";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("dept_name"), rs.getInt("total"));
            }
        }
        return result;
    }

    /**
     * Retourne un mapping Projet → Nombre d’employés affectés
     */
    public Map<String, Integer> employeesByProject() throws SQLException {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT p.name AS project_name, COUNT(pa.employee_id) AS total " +
                "FROM projects p " +
                "LEFT JOIN project_assignments pa ON pa.project_id = p.id " +
                "GROUP BY p.name ORDER BY p.name";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("project_name"), rs.getInt("total"));
            }
        }
        return result;
    }

    /**
     * Retourne un mapping Grade → Nombre d’employés
     */
    public Map<String, Integer> employeesByGrade() throws SQLException {
        Map<String, Integer> result = new LinkedHashMap<>();
        String sql = "SELECT grade, COUNT(id) AS total " +
                "FROM employees " +
                "GROUP BY grade ORDER BY grade";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("grade"), rs.getInt("total"));
            }
        }
        return result;
    }
}
