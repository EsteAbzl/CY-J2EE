package com.dao;

import com.model.Department;
import com.model.Payslip;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class PayslipDAO {
    private final Connection conn;
    public PayslipDAO(Connection c){ this.conn = c; }

    public void create(Payslip p) throws SQLException {
        String sql = "INSERT INTO payslips(employee_id, period_month, period_year, base_salary, bonuses, deductions, net_pay, generated_at) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, p.getEmployeeId());
            ps.setInt(2, p.getPeriodMonth());
            ps.setInt(3, p.getPeriodYear());
            ps.setBigDecimal(4, BigDecimal.valueOf(p.getBaseSalary()));
            ps.setDouble(5, p.getBonuses());
            ps.setDouble(6, p.getDeductions());
            ps.setDouble(7, p.getNetPay());
            ps.setTimestamp(8, p.getGeneratedAt());
            ps.executeUpdate();
        }
    }


    public List<Payslip> list(Integer employeeId, Integer year, Integer month) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * FROM payslips WHERE 1=1 ");
        List<Object> params = new ArrayList<>();
        if (employeeId != null) { sb.append("AND employee_id=? "); params.add(employeeId); }
        if (year != null) { sb.append("AND period_year=? "); params.add(year); }
        if (month != null) { sb.append("AND period_month=? "); params.add(month); }
        sb.append("ORDER BY period_year DESC, period_month DESC");
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                List<Payslip> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    public Payslip findById(int id) throws SQLException {
        String sql = "SELECT * FROM payslips WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Payslip p = new Payslip();
                    p.setId(rs.getInt("id"));
                    p.setEmployeeId(rs.getInt("employee_id"));
                    p.setPeriodMonth(rs.getInt("period_month"));
                    p.setPeriodYear(rs.getInt("period_year"));
                    p.setBaseSalary(rs.getBigDecimal("base_salary").doubleValue());
                    p.setBonuses(rs.getDouble("bonuses"));
                    p.setDeductions(rs.getDouble("deductions"));
                    p.setNetPay(rs.getDouble("net_pay"));
                    p.setGeneratedAt(rs.getTimestamp("generated_at"));
                    return p;
                }
            }
        }
        return null;
    }

    public List<Payslip> findAll() throws SQLException {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT * FROM payslips";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Payslip p = new Payslip();
                p.setId(rs.getInt("id"));
                p.setEmployeeId(rs.getInt("employee_id"));
                p.setPeriodMonth(rs.getInt("period_month"));
                p.setPeriodYear(rs.getInt("period_year"));
                BigDecimal salaryBD = rs.getBigDecimal("base_salary");
                if (salaryBD != null) {
                    p.setBaseSalary(salaryBD.doubleValue());
                }
                p.setBonuses(rs.getDouble("bonuses"));
                p.setDeductions(rs.getDouble("deductions"));
                p.setNetPay(rs.getDouble("net_pay"));
                p.setGeneratedAt(rs.getTimestamp("generated_at"));
                payslips.add(p);
            }
        }
        return payslips;
    }


    private Payslip map(ResultSet rs) throws SQLException {
        Payslip p = new Payslip();
        p.setId(rs.getInt("id"));
        p.setEmployeeId(rs.getInt("employee_id"));
        p.setPeriodYear(rs.getInt("period_year"));
        p.setPeriodMonth(rs.getInt("period_month"));
        p.setBaseSalary(rs.getDouble("base_salary"));
        p.setBonuses(rs.getDouble("bonuses"));
        p.setDeductions(rs.getDouble("deductions"));
        p.setNetPay(rs.getDouble("net_pay"));
        p.setGeneratedAt(rs.getTimestamp("generated_at"));
        return p;
    }

    public List<Payslip> findFiltered(Integer employeeId, Integer month, Integer year) throws SQLException {
        List<Payslip> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM payslips WHERE 1=1");

        if (employeeId != null) sql.append(" AND employee_id = ?");
        if (month != null) sql.append(" AND period_month = ?");
        if (year != null) sql.append(" AND period_year = ?");

        try (PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int idx = 1;
            if (employeeId != null) ps.setInt(idx++, employeeId);
            if (month != null) ps.setInt(idx++, month);
            if (year != null) ps.setInt(idx++, year);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Payslip p = new Payslip();
                    p.setId(rs.getInt("id"));
                    p.setEmployeeId(rs.getInt("employee_id"));
                    p.setPeriodYear(rs.getInt("period_year"));
                    p.setPeriodMonth(rs.getInt("period_month"));
                    p.setBaseSalary(rs.getBigDecimal("base_salary").doubleValue());
                    p.setBonuses(rs.getBigDecimal("bonuses").doubleValue());
                    p.setDeductions(rs.getBigDecimal("deductions").doubleValue());
                    p.setNetPay(rs.getBigDecimal("net_pay").doubleValue());
                    p.setGeneratedAt(rs.getTimestamp("generated_at"));
                    list.add(p);
                }
            }
        }
        return list;
    }

    public List<Payslip> findByEmployeeId(int employeeId) throws SQLException {
        List<Payslip> payslips = new ArrayList<>();
        String sql = "SELECT id, employee_id, period_month, period_year, net_pay, generated_at " +
                "FROM payslips WHERE employee_id = ? ORDER BY period_year DESC, period_month DESC";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, employeeId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Payslip psObj = new Payslip();
                    psObj.setId(rs.getInt("id"));
                    psObj.setEmployeeId(rs.getInt("employee_id"));
                    psObj.setPeriodMonth(rs.getInt("period_month"));
                    psObj.setPeriodYear(rs.getInt("period_year"));
                    psObj.setNetPay(rs.getBigDecimal("net_pay").doubleValue());
                    psObj.setGeneratedAt(rs.getTimestamp("generated_at"));
                    payslips.add(psObj);
                }
            }
        }
        return payslips;
    }

}
