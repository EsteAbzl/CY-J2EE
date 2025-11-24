package com.servlet;

import com.dao.ProjectAssignmentDAO;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ProjectUnassignServlet")
public class ProjectUnassignServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String projectIdStr = req.getParameter("project_id");
        String employeeIdStr = req.getParameter("employee_id");

        if (projectIdStr == null || employeeIdStr == null) {
            resp.sendRedirect("projectsList.jsp?error=missingParams");
            return;
        }

        int projectId = Integer.parseInt(projectIdStr);
        int employeeId = Integer.parseInt(employeeIdStr);

        try (Connection conn = DBConnection.getConnection()) {
            ProjectAssignmentDAO dao = new ProjectAssignmentDAO(conn);
            dao.removeEmployeeFromProject(projectId, employeeId);

            // Redirection vers la liste des membres du projet
            resp.sendRedirect("ProjectMembersServlet?id=" + projectId + "&success=unassigned");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la désaffectation de l'employé du projet", e);
        }
    }
}
