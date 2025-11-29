package com.dao;

import com.model.Salaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaireDAO {
    private final Connection conn;

    public SalaireDAO(Connection c) {
        this.conn = c;
    }

    public void create(Salaire s) throws SQLException {
        String sql = "INSERT INTO salaire(salaire, date, employee_id) VALUES(?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, s.getSalaire());
            ps.setDate(2, s.getDate());
            ps.setInt(3, s.getEmployeeId());
            ps.executeUpdate();
        }
    }

    public Salaire findById(int id) throws SQLException {
        String sql = "SELECT * FROM salaire WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Salaire> findAll() throws SQLException {
        List<Salaire> salaires = new ArrayList<>();
        String sql = "SELECT * FROM salaire";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                salaires.add(map(rs));
            }
        }
        return salaires;
    }

    public List<Salaire> findByEmployeeId(int employeeId) throws SQLException {
        List<Salaire> salaires = new ArrayList<>();
        String sql = "SELECT * FROM salaire WHERE employee_id = ? ORDER BY date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    salaires.add(map(rs));
                }
            }
        }
        return salaires;
    }

    /**
     * Trouve le salaire le plus proche dans le passé pour un employé à une date donnée
     */
    public Salaire findMostRecentSalaire(int employeeId, java.sql.Date referenceDate) throws SQLException {
        String sql = "SELECT * FROM salaire WHERE employee_id = ? AND date <= ? ORDER BY date DESC LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setDate(2, referenceDate);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public java.sql.Date findFirstSalaryDate(int employeeId) throws SQLException {
        String sql = "SELECT s.date FROM salaire s WHERE employee_id = ? AND s.date <= all(SELECT s2.date FROM salaire s2 WHERE employee_id = ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setInt(2, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDate("s.date");
                }
            }
        }
        return null;
    }

    public void update(Salaire s) throws SQLException {
        String sql = "UPDATE salaire SET salaire=?, date=?, employee_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, s.getSalaire());
            ps.setDate(2, s.getDate());
            ps.setInt(3, s.getEmployeeId());
            ps.setInt(4, s.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM salaire WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Salaire map(ResultSet rs) throws SQLException {
        Salaire s = new Salaire();
        s.setId(rs.getInt("id"));
        s.setSalaire(rs.getDouble("salaire"));
        s.setDate(rs.getDate("date"));
        s.setEmployeeId(rs.getInt("employee_id"));
        return s;
    }
}
