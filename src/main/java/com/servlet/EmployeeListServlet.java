package com.servlet;

import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/EmployeeListServlet")
public class EmployeeListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String q = req.getParameter("q");
        String grade = req.getParameter("grade");
        String position = req.getParameter("position");
        Integer dep = null;
        try { dep = req.getParameter("departmentId") != null ? Integer.parseInt(req.getParameter("departmentId")) : null; }
        catch (NumberFormatException ignored) {}

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            List<Employee> employees = dao.findAll();
            req.setAttribute("employees", employees);
            req.getRequestDispatcher("employeesList.jsp").forward(req, resp);
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
    }
}