package com.servlet;

import com.dao.ReportsDAO;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;


@WebServlet("/ReportsServlet")
public class ReportsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            ReportsDAO dao = new ReportsDAO(conn);

            Map<String,Integer> byDept = dao.employeesByDepartment();
            Map<String,Integer> byProj = dao.employeesByProject();
            Map<String,Integer> byGrade = dao.employeesByGrade();

            req.setAttribute("employeesByDepartment", byDept);
            req.setAttribute("employeesByProject", byProj);
            req.setAttribute("employeesByGrade", byGrade);

            req.getRequestDispatcher("reports.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des statistiques", e);
        }
    }
}

