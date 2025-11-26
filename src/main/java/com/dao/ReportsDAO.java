package com.dao;

import java.sql.*;
import java.util.*;

public class ReportsDAO {
    private Connection conn;

    public ReportsDAO(Connection conn) {
        this.conn = conn;
    }

    public Map<String,Integer> employeesByDepartment() throws SQLException {
        Map<String,Integer> result = new HashMap<>();
        String sql = "SELECT d.name, COUNT(e.id) AS nb " +
                "FROM departments d " +
                "LEFT JOIN employees e ON e.department_id = d.id " +
                "GROUP BY d.name";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("name"), rs.getInt("nb"));
            }
        }
        return result;
    }

    public Map<String,Integer> employeesByProject() throws SQLException {
        Map<String,Integer> result = new HashMap<>();
        String sql = "SELECT p.name, COUNT(pa.employee_id) AS nb " +
                "FROM projects p " +
                "LEFT JOIN project_assignments pa ON pa.project_id = p.id " +
                "GROUP BY p.name";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("name"), rs.getInt("nb"));
            }
        }
        return result;
    }

    public Map<String,Integer> employeesByGrade() throws SQLException {
        Map<String,Integer> result = new HashMap<>();
        String sql = "SELECT e.grade, COUNT(e.id) AS nb " +
                "FROM employees e " +
                "GROUP BY e.grade";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                result.put(rs.getString("grade"), rs.getInt("nb"));
            }
        }
        return result;
    }
}

