package com.servlet;

import com.dao.EmployeeDAO;
import com.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/EmployeeDeleteServlet")
public class EmployeeDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("employeeList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            dao.delete(id);
            resp.sendRedirect("dashboard.jsp?success=delete");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de la suppression de l'employé", e);
        }
    }
}


