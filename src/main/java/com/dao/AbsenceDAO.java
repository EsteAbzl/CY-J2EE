package com.dao;

import com.model.Absence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AbsenceDAO {
    private final Connection conn;

    public AbsenceDAO(Connection conn) {
        this.conn = conn;
    }

    // Récupérer toutes les absences d’un employé
    public List<Absence> findByEmployee(int employeeId) throws SQLException {
        List<Absence> list = new ArrayList<>();
        String sql = "SELECT * FROM absences WHERE employee_id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    // Ajouter une absence
    public void create(Absence a) throws SQLException {
        String sql = "INSERT INTO absences(employee_id, date, type, hours) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, a.getEmployeeId());
            ps.setDate(2, a.getDate());
            ps.setString(3, a.getType());
            ps.setInt(4, a.getHours());
            ps.executeUpdate();
        }
    }

    private Absence map(ResultSet rs) throws SQLException {
        Absence a = new Absence();
        a.setId(rs.getInt("id"));
        a.setEmployeeId(rs.getInt("employee_id"));
        a.setDate(rs.getDate("date"));
        a.setType(rs.getString("type"));
        a.setHours(rs.getInt("hours"));
        return a;
    }

    public void save(Absence absence) throws SQLException {
        String sql = "INSERT INTO absences (employee_id, date, type, hours) VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, absence.getEmployeeId());
            ps.setDate(2, java.sql.Date.valueOf(absence.getDate().toLocalDate()));
            ps.setString(3, absence.getType());
            ps.setInt(4, absence.getHours());
            ps.executeUpdate();
        }
    }

    public List<Absence> findByEmployeeId(int employeeId) throws SQLException {
        List<Absence> absences = new ArrayList<>();
        String sql = "SELECT * FROM absences WHERE employee_id = ? ORDER BY date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Absence absence = new Absence();
                    absence.setId(rs.getInt("id"));
                    absence.setEmployeeId(rs.getInt("employee_id"));
                    absence.setDate(Date.valueOf(rs.getDate("date").toLocalDate()));
                    absence.setType(rs.getString("type"));
                    absence.setHours(rs.getInt("hours"));
                    absences.add(absence);
                }
            }
        }
        return absences;
    }

}
