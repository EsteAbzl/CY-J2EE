package com.servlet;

import com.dao.ProjectAssignmentDAO;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ProjectAssignmentServlet")
public class ProjectAssignmentServlet extends HttpServlet {

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
