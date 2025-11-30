package com.servlet;

import com.dao.ProjectAssignmentDAO;
import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet("/ProjectAssignmentServlet")
public class ProjectAssignmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String projectIdStr = req.getParameter("project_id");
        if (projectIdStr == null || projectIdStr.isBlank()) {
            resp.sendRedirect("ProjectsListServlet?error=missingProjectId");
            return;
        }

        int projectId = Integer.parseInt(projectIdStr);

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO employeeDao = new EmployeeDAO(conn);
            ProjectAssignmentDAO assignmentDao = new ProjectAssignmentDAO(conn);
            
            // Récupérer tous les employés
            List<Employee> allEmployees = employeeDao.findAll();
            
            // Récupérer les IDs des employés déjà assignés au projet
            List<Integer> assignedEmployeeIds = assignmentDao.getAssignedEmployeeIds(projectId);
            
            // Filtrer les employés pour exclure ceux déjà assignés
            List<Employee> availableEmployees = new ArrayList<>();
            for (Employee e : allEmployees) {
                if (!assignedEmployeeIds.contains(e.getId())) {
                    availableEmployees.add(e);
                }
            }

            req.setAttribute("project_id", projectIdStr);
            req.setAttribute("employees", availableEmployees);
            req.getRequestDispatcher("projectAssignment.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des employés", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupération des paramètres du formulaire
        int projectId = Integer.parseInt(req.getParameter("project_id"));
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));
        String role = req.getParameter("role_in_project");

        try (Connection conn = DBConnection.getConnection()) {
            ProjectAssignmentDAO dao = new ProjectAssignmentDAO(conn);
            dao.assignEmployeeToProject(projectId, employeeId, role);

            // Redirection après succès
            resp.sendRedirect("ProjectMembersServlet?id=" + projectId + "&success=assigned");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de l'affectation de l'employé au projet", e);
        }
    }
}
