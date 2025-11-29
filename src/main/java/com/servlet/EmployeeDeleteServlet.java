package com.servlet;

import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.List;

@WebServlet("/EmployeeDeleteServlet")
public class EmployeeDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            if (idStr == null || idStr.isBlank()) {
                List<Employee> employees = dao.findAll();
                req.setAttribute("employees", employees);
                req.getRequestDispatcher("deleteEmployee.jsp").forward(req, resp);
                return;
            }

            int id = Integer.parseInt(idStr);
            dao.delete(id);
            resp.sendRedirect("dashboard.jsp?success=delete");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de la suppression de l'employé", e);
        }
    }
}


