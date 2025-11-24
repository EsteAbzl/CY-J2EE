package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.List;

import com.dao.DepartmentDAO;
import com.model.Department;
import com.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;

@WebServlet("/DepartmentsListServlet")
public class DepartmentsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO dao = new DepartmentDAO(conn);
            List<Department> departments = dao.findAll();

            req.setAttribute("departments", departments);
            req.getRequestDispatcher("departmentsList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des départements", e);
        }
    }
}

