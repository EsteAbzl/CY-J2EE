package com.dao;

import com.model.SalaireExtra;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaireExtraDAO {
    private final Connection conn;

    public SalaireExtraDAO(Connection c) {
        this.conn = c;
    }

    public void create(SalaireExtra se) throws SQLException {
        String sql = "INSERT INTO salaire_extra(montant, motif, date, employee_id) VALUES(?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, se.getMontant());
            ps.setString(2, se.getMotif());
            ps.setDate(3, se.getDate());
            ps.setInt(4, se.getEmployeeId());
            ps.executeUpdate();
        }
    }

    public SalaireExtra findById(int id) throws SQLException {
        String sql = "SELECT * FROM salaire_extra WHERE id = ?";
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

    public List<SalaireExtra> findAll() throws SQLException {
        List<SalaireExtra> salairesExtra = new ArrayList<>();
        String sql = "SELECT * FROM salaire_extra";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                salairesExtra.add(map(rs));
            }
        }
        return salairesExtra;
    }

    public List<SalaireExtra> findByEmployeeId(int employeeId) throws SQLException {
        List<SalaireExtra> salairesExtra = new ArrayList<>();
        String sql = "SELECT * FROM salaire_extra WHERE employee_id = ? ORDER BY date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    salairesExtra.add(map(rs));
                }
            }
        }
        return salairesExtra;
    }

    /**
     * Trouve tous les salaires extras pour un employé à un mois et année donnés
     */
    public List<SalaireExtra> findByEmployeeAndPeriod(int employeeId, int year, int month) throws SQLException {
        List<SalaireExtra> salairesExtra = new ArrayList<>();
        String sql = "SELECT * FROM salaire_extra WHERE employee_id = ? AND YEAR(date) = ? AND MONTH(date) = ? ORDER BY date DESC";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            ps.setInt(2, year);
            ps.setInt(3, month);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    salairesExtra.add(map(rs));
                }
            }
        }
        return salairesExtra;
    }

    public void update(SalaireExtra se) throws SQLException {
        String sql = "UPDATE salaire_extra SET montant=?, motif=?, date=?, employee_id=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDouble(1, se.getMontant());
            ps.setString(2, se.getMotif());
            ps.setDate(3, se.getDate());
            ps.setInt(4, se.getEmployeeId());
            ps.setInt(5, se.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM salaire_extra WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private SalaireExtra map(ResultSet rs) throws SQLException {
        SalaireExtra se = new SalaireExtra();
        se.setId(rs.getInt("id"));
        se.setMontant(rs.getDouble("montant"));
        se.setMotif(rs.getString("motif"));
        se.setDate(rs.getDate("date"));
        se.setEmployeeId(rs.getInt("employee_id"));
        return se;
    }
}
