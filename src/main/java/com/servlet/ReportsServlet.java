package com.servlet;

import com.dao.ReportsDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class ReportsServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Connection conn = (Connection) getServletContext().getAttribute("DBConnection");
        ReportsDAO dao = new ReportsDAO(conn);

        try {
            Map<String,Integer> byDept = dao.employeesByDepartment();
            Map<String,Integer> byProj = dao.employeesByProject();
            Map<String,Integer> byGrade = dao.employeesByGrade();

            req.setAttribute("employeesByDepartment", byDept);
            req.setAttribute("employeesByProject", byProj);
            req.setAttribute("employeesByGrade", byGrade);

            req.getRequestDispatcher("/jsp/reports.jsp").forward(req, resp);
        } catch (Exception e) { throw new ServletException(e); }
    }
}
