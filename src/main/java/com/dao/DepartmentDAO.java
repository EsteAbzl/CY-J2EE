package com.dao;

import com.model.Department;
import com.util.HibernateUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.sql.*;
import java.util.*;

public class DepartmentDAO {
    private final Connection conn;
    public DepartmentDAO(Connection conn) { this.conn = conn; }

    public List<Department> findAll() throws SQLException {
        List<Department> departments = new ArrayList<>();
        String sql = "SELECT * FROM departments";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Department d = new Department();
                d.setId(rs.getInt("id"));
                d.setName(rs.getString("name"));
                d.setDescription(rs.getString("description"));
                departments.add(d);
            }
        }
        return departments;
    }

    public Department findById(int id) throws SQLException {
        String sql = "SELECT * FROM departments WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Department dept = new Department();
                    dept.setId(rs.getInt("id"));
                    dept.setName(rs.getString("name"));
                    dept.setDescription(rs.getString("description"));
                    return dept;
                }
            }
        }
        return null;
    }

    public void create(Department d) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO departments(name,description) VALUES(?,?)")) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getDescription());
            ps.executeUpdate();
        }
    }

    public void update(Department d) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("UPDATE departments SET name=?, description=? WHERE id=?")) {
            ps.setString(1, d.getName());
            ps.setString(2, d.getDescription());
            ps.setInt(3, d.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM departments WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Department map(ResultSet rs) throws SQLException {
        Department d = new Department();
        d.setId(rs.getInt("id"));
        d.setName(rs.getString("name"));
        d.setDescription(rs.getString("description"));
        return d;
    }


}
