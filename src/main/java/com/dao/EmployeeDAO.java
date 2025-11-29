package com.dao;

import com.model.Employee;
import com.model.Salaire;
import com.util.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {

    private final Connection conn;
    public EmployeeDAO(Connection c) { this.conn = c; }

    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE active = true";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Employee emp = new Employee();
                emp.setId(rs.getInt("id"));
                emp.setFirstName(rs.getString("first_name"));
                emp.setLastName(rs.getString("last_name"));
                emp.setEmail(rs.getString("email"));
                emp.setGrade(rs.getString("grade"));
                emp.setPositionTitle(rs.getString("position_title"));
                emp.setDepartmentId(rs.getInt("department_id"));
                employees.add(emp);
            }
        }
        return employees;
    }

    public List<Employee> search(String query, String grade, String position, Integer departmentId) throws SQLException {
        StringBuilder sb = new StringBuilder("SELECT * FROM employees WHERE active = true ");
        List<Object> params = new ArrayList<>();
        if (query != null && !query.isEmpty()) {
            sb.append("AND (first_name LIKE ? OR last_name LIKE ? OR email LIKE ?) ");
            String like = "%" + query + "%";
            params.add(like); params.add(like); params.add(like); params.add(like);
        }
        if (grade != null && !grade.isEmpty()) { sb.append("AND grade = ? "); params.add(grade); }
        if (position != null && !position.isEmpty()) { sb.append("AND position_title = ? "); params.add(position); }
        if (departmentId != null) { sb.append("AND department_id = ? "); params.add(departmentId); }
        sb.append("ORDER BY last_name, first_name");
        try (PreparedStatement ps = conn.prepareStatement(sb.toString())) {
            for (int i = 0; i < params.size(); i++) ps.setObject(i + 1, params.get(i));
            try (ResultSet rs = ps.executeQuery()) {
                List<Employee> list = new ArrayList<>();
                while (rs.next()) list.add(map(rs));
                return list;
            }
        }
    }

    public Employee findById(int id) throws SQLException {
        String sql = "SELECT * FROM employees WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setEmail(rs.getString("email"));
                    emp.setGrade(rs.getString("grade"));
                    emp.setPositionTitle(rs.getString("position_title"));
                    emp.setBaseSalary(rs.getBigDecimal("base_salary").doubleValue());
                    emp.setDepartmentId(rs.getInt("department_id"));
                    emp.setActive(rs.getBoolean("active"));
                    return emp;
                }
            }
        }
        return null;
    }

    public List<String> findDistinctGrades() throws SQLException {
        List<String> grades = new ArrayList<>();
        String sql = "SELECT DISTINCT grade FROM employees WHERE grade IS NOT NULL AND active = true";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                grades.add(rs.getString("grade"));
            }
        }
        return grades;
    }

    public List<String> findDistinctPositions() throws SQLException {
        List<String> positions = new ArrayList<>();
        String sql = "SELECT DISTINCT position_title FROM employees WHERE position_title IS NOT NULL AND active = true";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                positions.add(rs.getString("position_title"));
            }
        }
        return positions;
    }

    public void create(Employee e) throws SQLException {
        String sql = "INSERT INTO employees(first_name, last_name, email, grade, position_title, base_salary, department_id, active) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getGrade());
            ps.setString(5, e.getPositionTitle());
            ps.setBigDecimal(6, BigDecimal.valueOf(e.getBaseSalary()));
            if (e.getDepartmentId() == 0) {
                ps.setNull(7, Types.INTEGER);
            } else {
                ps.setInt(7, e.getDepartmentId());
            }
            ps.setBoolean(8, e.isActive());
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    // set generated id back on the entity
                    e.setId(keys.getInt(1));
                }
            }
        }
    }


    public void update(Employee e) throws SQLException {
        String sql = "UPDATE employees SET first_name=?, last_name=?, email=?, grade=?, position_title=?, base_salary=?, department_id=?, active=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, e.getFirstName());
            ps.setString(2, e.getLastName());
            ps.setString(3, e.getEmail());
            ps.setString(4, e.getGrade());
            ps.setString(5, e.getPositionTitle());
            ps.setDouble(6, e.getBaseSalary());
            ps.setInt(7, e.getDepartmentId());
            ps.setBoolean(8, e.isActive());
            ps.setInt(9, e.getId());
            ps.executeUpdate();
        }
    }


    public void delete(int id) throws SQLException {
        String sql = "UPDATE employees SET active=false WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Employee map(ResultSet rs) throws SQLException {
        Employee e = new Employee();
        e.setId(rs.getInt("id"));
        e.setFirstName(rs.getString("first_name"));
        e.setLastName(rs.getString("last_name"));
        e.setEmail(rs.getString("email"));
        e.setGrade(rs.getString("grade"));
        e.setPositionTitle(rs.getString("position_title"));
        e.setBaseSalary(rs.getDouble("base_salary"));
        int dep = rs.getInt("department_id");
        e.setDepartmentId(rs.wasNull() ? null : dep);
        e.setActive(rs.getBoolean("active"));
        return e;
    }

    public List<Employee> searchByDepartment(int departmentId) throws SQLException {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT * FROM employees WHERE department_id = ? AND active = true";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, departmentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("id"));
                    emp.setFirstName(rs.getString("first_name"));
                    emp.setLastName(rs.getString("last_name"));
                    emp.setEmail(rs.getString("email"));
                    emp.setGrade(rs.getString("grade"));
                    emp.setPositionTitle(rs.getString("position_title"));
                    emp.setBaseSalary(rs.getBigDecimal("base_salary").doubleValue());
                    emp.setDepartmentId(rs.getInt("department_id"));
                    emp.setActive(rs.getBoolean("active"));
                    employees.add(emp);
                }
            }
        }
        return employees;
    }

    public Date arrivingDate(int employeeId) throws SQLException{
        SalaireDAO salaireDAO = new SalaireDAO(conn);
        return salaireDAO.findFirstSalaryDate(employeeId);
    }

    public Employee findByEmail(String email) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            // NOTE: use JPQL with entity name 'Employee'
            TypedQuery<Employee> q = em.createQuery("SELECT e FROM Employee e WHERE e.email = :email", Employee.class);
            q.setParameter("email", email);
            return q.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        } finally {
            em.close();
        }
    }

    /**
     * Check existence of an email using plain SQL (fast, safe for unique check before insert).
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT 1 FROM employees WHERE email = ? LIMIT 1";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public Employee save(Employee employe) {
        EntityManager em = HibernateUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (employe.getId() == null) {
                em.persist(employe);
            } else {
                employe = em.merge(employe);
            }
            em.getTransaction().commit();
            return employe;
        } catch (Exception ex) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw ex;
        } finally {
            em.close();
        }
    }
}
