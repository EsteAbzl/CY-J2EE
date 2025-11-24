package com.servlet;

import java.io.IOException;
import java.sql.*;

import com.dao.ProjectDAO;
import com.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;

@WebServlet("/ProjectDeleteServlet")
public class ProjectDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("projectsList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn); // passe la connexion
            dao.delete(id);
            resp.sendRedirect("dashboard.jsp?success=delete");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la suppression du projet", e);
        }
    }
}

