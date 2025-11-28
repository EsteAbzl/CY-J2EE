package com.servlet;

import com.dao.EmployeeDAO;
import com.dao.DepartmentDAO;
import com.model.Department;
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
        try {
            String depParam = req.getParameter("department");
            if (depParam != null && !depParam.isBlank()) {
                dep = Integer.parseInt(depParam);
            }
        }
        catch (NumberFormatException ignored) {}

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            DepartmentDAO departmentDao = new DepartmentDAO(conn);

            List<Employee> employees = dao.search(q, grade, position, dep);

            List<String> grades = dao.findDistinctGrades();
            List<String> positions = dao.findDistinctPositions();
            List<Department> departments = departmentDao.findAll();

            req.setAttribute("employees", employees);
            req.setAttribute("grades", grades);
            req.setAttribute("positions", positions);
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("employeesList.jsp").forward(req, resp);
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
    }
}