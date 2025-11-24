package com.servlet;

import com.dao.ProjectDAO;
import com.model.Employee;
import com.util.DBConnection;
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
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("projectsList.jsp?error=missingId");
            return;
        }

        int projectId = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn);
            List<Employee> members = dao.findMembers(projectId);

            req.setAttribute("members", members);
            req.getRequestDispatcher("projectMembers.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des membres du projet", e);
        }
    }
}

