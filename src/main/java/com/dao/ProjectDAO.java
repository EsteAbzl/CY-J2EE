package com.dao;

import com.model.Employee;
import com.model.Project;
import java.sql.*;
import java.util.*;

public class ProjectDAO {
    private final Connection conn;
    public ProjectDAO(Connection c){ this.conn = c; }

    public List<Project> findAll() throws SQLException {
        List<Project> projects = new ArrayList<>();
        String sql = "SELECT * FROM projects";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Project p = new Project();
                p.setId(rs.getInt("id"));
                p.setName(rs.getString("name"));
                p.setDescription(rs.getString("description"));
                p.setStartDate(rs.getDate("start_date"));
                p.setEndDate(rs.getDate("end_date"));
                p.setDepartmentId(rs.getInt("department_id"));
                p.setStatus(rs.getString("status"));
                projects.add(p);
            }
        }
        return projects;
    }

    public void create(Project p) throws SQLException {
        String sql = "INSERT INTO projects (name, description, status, start_date, end_date, department_id) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setString(3, p.getStatus()); // doit être une valeur valide
            ps.setDate(4, p.getStartDate());
            ps.setDate(5, p.getEndDate());
            ps.setInt(6, p.getDepartmentId());

            ps.executeUpdate();
        }
    }

    public void update(Project project) throws SQLException {
        String sql = "UPDATE projects SET name=?, description=?, start_date=?, end_date=?, department_id=?, status=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, project.getName());
            ps.setString(2, project.getDescription());
            ps.setDate(3, project.getStartDate());
            ps.setDate(4, project.getEndDate());
            ps.setInt(5, project.getDepartmentId());
            ps.setString(6, project.getStatus());
            ps.setInt(7, project.getId());
            ps.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM projects WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Project map(ResultSet rs) throws SQLException {
        Project p = new Project();
        p.setId(rs.getInt("id"));
        p.setName(rs.getString("name"));
        p.setDescription(rs.getString("description"));
        p.setStatus(rs.getString("status"));
        p.setStartDate(rs.getDate("start_date"));
        p.setEndDate(rs.getDate("end_date"));
        int dep = rs.getInt("department_id");
        p.setDepartmentId(rs.wasNull()? null : dep);
        return p;
    }

    public Project findById(int id) throws SQLException {
        String sql = "SELECT * FROM projects WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Project p = new Project();
                    p.setId(rs.getInt("id"));
                    p.setName(rs.getString("name"));
                    p.setDescription(rs.getString("description"));
                    p.setStartDate(rs.getDate("start_date"));
                    p.setEndDate(rs.getDate("end_date"));
                    p.setDepartmentId(rs.getInt("department_id"));
                    p.setStatus(rs.getString("status"));
                    return p;
                }
            }
        }
        return null;
    }

    public List<Employee> findMembers(int projectId) throws SQLException {
        List<Employee> members = new ArrayList<>();
        String sql = "SELECT e.id, e.first_name, e.last_name, e.email, pa.role_in_project " +
                "FROM employees e " +
                "JOIN project_assignments pa ON e.id = pa.employee_id " +
                "WHERE pa.project_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setEmail(rs.getString("email"));
                    emp.setRoleInProject(rs.getString("role_in_project"));
                    members.add(emp);
                }
            }
        }
        return members;
    }


}
