package com.servlet;

import com.dao.ProjectDAO;
import com.model.Project;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/ProjectEditServlet")
public class ProjectEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("projectsList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn); //  passe la connexion
            Project project = dao.findById(id);

            if (project == null) {
                resp.sendRedirect("projectsList.jsp?error=notFound");
                return;
            }

            req.setAttribute("project", project);
            req.getRequestDispatcher("editProject.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement du projet", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String startDateStr = req.getParameter("start_date");
        String endDateStr = req.getParameter("end_date");
        int deptId = Integer.parseInt(req.getParameter("department_id"));
        String status = req.getParameter("status");

        Project project = new Project();
        project.setId(id);
        project.setName(name);
        project.setDescription(description);
        project.setStartDate(java.sql.Date.valueOf(startDateStr));
        project.setEndDate(java.sql.Date.valueOf(endDateStr));
        project.setDepartmentId(deptId);
        project.setStatus(status);

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn);
            dao.update(project);
            resp.sendRedirect("dashboard.jsp?success=update");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la mise à jour du projet", e);
        }
    }
}
