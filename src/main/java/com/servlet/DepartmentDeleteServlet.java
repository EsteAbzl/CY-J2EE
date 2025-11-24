package com.servlet;

import com.dao.DepartmentDAO;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/DepartmentDeleteServlet")
public class DepartmentDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("departmentsList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO dao = new DepartmentDAO(conn); // passe la connexion
            dao.delete(id);
            resp.sendRedirect("dashboard.jsp?success=delete");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la suppression du département", e);
        }
    }
}
