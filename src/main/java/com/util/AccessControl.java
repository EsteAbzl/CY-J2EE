package com.util;

import com.model.User;
import com.model.Employee;
import com.dao.EmployeeDAO;
import com.dao.ProjectDAO;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utilitaire pour le contrôle d'accès basé sur les rôles et départements
 */
public class AccessControl {

    // Constantes des rôles
    public static final int ROLE_ADMIN = 1;
    public static final int ROLE_DEPT_HEAD = 2;
    public static final int ROLE_PROJECT_HEAD = 3;
    public static final int ROLE_EMPLOYEE = 4;

    /**
     * Vérifie si l'utilisateur est un administrateur ou un employé RH
     */
    public static boolean isAdmin(User user) {
        return user != null && user.getRoleId() == ROLE_ADMIN;
    }

    /**
     * Vérifie si l'utilisateur est un chef de département
     */
    public static boolean isDepartmentHead(User user) {
        return user != null && user.getRoleId() == ROLE_DEPT_HEAD;
    }

    /**
     * Vérifie si l'utilisateur est un chef de projet
     */
    public static boolean isProjectHead(User user) {
        return user != null && user.getRoleId() == ROLE_PROJECT_HEAD;
    }

    /**
     * Vérifie si l'utilisateur est un employé lambda
     */
    public static boolean isEmployee(User user) {
        return user != null && user.getRoleId() == ROLE_EMPLOYEE;
    }

    /**
     * Vérifie si l'utilisateur est un administrateur ou RH
     */
    public static boolean isAdminOrRH(User user) {
        return isAdmin(user);
    }

    /**
     * Vérifie si un utilisateur peut accéder à un département
     * - ADMIN : accès à tous les départements
     * - DEPT_HEAD : accès seulement à son département
     * - PROJECT_HEAD/EMPLOYEE : pas d'accès direct aux départements (mais peuvent voir via projets)
     */
    public static boolean canAccessDepartment(User user, Integer departmentId, Connection conn) throws SQLException {
        if (isAdmin(user)) {
            return true;
        }

        if (isDepartmentHead(user) && user.getEmployeeId() != null) {
            EmployeeDAO empDao = new EmployeeDAO(conn);
            Employee emp = empDao.findById(user.getEmployeeId());
            return emp != null && emp.getDepartmentId() != null && emp.getDepartmentId().equals(departmentId);
        }

        return false;
    }

    /**
     * Vérifie si un utilisateur peut accéder à un projet
     * - ADMIN : accès à tous les projets
     * - DEPT_HEAD : accès aux projets de son département
     * - PROJECT_HEAD : accès aux projets où il est assigné comme chef de projet
     * - EMPLOYEE : accès aux projets où il est assigné
     */
    public static boolean canAccessProject(User user, int projectId, Connection conn) throws SQLException {
        if (isAdmin(user)) {
            return true;
        }

        ProjectDAO projectDao = new ProjectDAO(conn);
        com.model.Project project = projectDao.findById(projectId);
        
        if (project == null) {
            return false;
        }

        if (isDepartmentHead(user) && user.getEmployeeId() != null) {
            EmployeeDAO empDao = new EmployeeDAO(conn);
            Employee emp = empDao.findById(user.getEmployeeId());
            return emp != null && emp.getDepartmentId() != null && 
                   emp.getDepartmentId().equals(project.getDepartmentId());
        }

        if (isProjectHead(user) && user.getEmployeeId() != null) {
            // Vérifier si cet utilisateur est chef de projet pour ce projet
            return isProjectLeadForProject(user.getEmployeeId(), projectId, conn);
        }

        if (isEmployee(user) && user.getEmployeeId() != null) {
            // Vérifier si cet employé est assigné au projet
            return isEmployeeAssignedToProject(user.getEmployeeId(), projectId, conn);
        }

        return false;
    }

    /**
     * Vérifie si un employé est chef de projet pour un projet donné
     */
    public static boolean isProjectLeadForProject(Integer employeeId, int projectId, Connection conn) throws SQLException {
        if (employeeId == null) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM project_assignments WHERE project_id = ? AND employee_id = ? AND role_in_project = 'Chef de projet'";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si un employé est assigné à un projet
     */
    public static boolean isEmployeeAssignedToProject(Integer employeeId, int projectId, Connection conn) throws SQLException {
        if (employeeId == null) {
            return false;
        }

        String sql = "SELECT COUNT(*) FROM project_assignments WHERE project_id = ? AND employee_id = ?";
        try (java.sql.PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, projectId);
            ps.setInt(2, employeeId);
            try (java.sql.ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    /**
     * Vérifie si un utilisateur peut gérer les employés
     * - ADMIN : oui
     * - DEPT_HEAD : oui (pour son département)
     * - PROJECT_HEAD/EMPLOYEE : non
     */
    public static boolean canManageEmployees(User user) {
        return isAdmin(user) || isDepartmentHead(user);
    }

    /**
     * Vérifie si un utilisateur peut gérer les départements
     * - ADMIN : oui
     * - Autres : non
     */
    public static boolean canManageDepartments(User user) {
        return isAdmin(user);
    }

    /**
     * Vérifie si un utilisateur peut gérer les projets
     * - ADMIN : oui
     * - DEPT_HEAD : oui (pour son département)
     * - PROJECT_HEAD : oui (pour les projets où il est chef)
     * - EMPLOYEE : non
     */
    public static boolean canManageProjects(User user) {
        return isAdmin(user) || isDepartmentHead(user) || isProjectHead(user);
    }
}
