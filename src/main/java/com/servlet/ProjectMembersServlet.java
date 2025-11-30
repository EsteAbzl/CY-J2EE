package com.servlet;

import com.dao.ProjectAssignmentDAO;
import com.dao.ProjectDAO;
import com.model.Employee;
import com.model.Project;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ProjectMembersServlet")
public class ProjectMembersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req));

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("projectsList.jsp?error=missingId");
            return;
        }

        int projectId = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn);

            // Récupération du projet lui-même
            Project project = dao.findById(projectId);

            // Récupération des membres avec leur rôle
            List<Employee> members = dao.findMembers(projectId);

            req.setAttribute("project", project);
            req.setAttribute("members", members);

            req.getRequestDispatcher("projectMembers.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des membres du projet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String action = req.getParameter("action");
        
        if ("remove".equals(action)) {
            int projectId = Integer.parseInt(req.getParameter("project_id"));
            int employeeId = Integer.parseInt(req.getParameter("employee_id"));

            try (Connection conn = DBConnection.getConnection()) {
                ProjectAssignmentDAO dao = new ProjectAssignmentDAO(conn);
                dao.removeEmployeeFromProject(projectId, employeeId);
                resp.sendRedirect("ProjectMembersServlet?id=" + projectId + "&success=removed");
            } catch (SQLException e) {
                throw new ServletException("Erreur lors de la suppression de l'employé du projet", e);
            }
        }
    }
}
