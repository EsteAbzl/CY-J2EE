package com.servlet;

import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.DBConnection;
import com.util.ValidationUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/EmployeeCreateServlet")
public class EmployeeCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email");
        String grade = req.getParameter("grade");
        String positionTitle = req.getParameter("position_title");
        String baseSalaryStr = req.getParameter("base_salary");
        String departmentIdStr = req.getParameter("department_id");

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);

            Employee emp = new Employee();
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setEmail(email);
            emp.setGrade(grade);
            emp.setPositionTitle(positionTitle);
            java.math.BigDecimal salary = new java.math.BigDecimal(baseSalaryStr);
            emp.setBaseSalary(salary.doubleValue());
            if (departmentIdStr == null || departmentIdStr.isBlank()) {
                emp.setDepartmentId(0);
            }
            else {
                emp.setDepartmentId(Integer.parseInt(departmentIdStr));
                emp.setActive(true);
            }
            dao.create(emp);

            resp.sendRedirect("dashboard.jsp");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de l'ajout d'un employé", e);
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/employees/form.jsp").forward(req, resp);
    }
}
