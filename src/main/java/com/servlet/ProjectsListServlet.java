package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.dao.ProjectDAO;
import com.model.Project;
import com.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;

@WebServlet("/ProjectsListServlet")
public class ProjectsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn); // passe la connexion
            List<Project> projects = dao.findAll();

            req.setAttribute("projects", projects);
            req.getRequestDispatcher("projectsList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des projets", e);
        }
    }
}
