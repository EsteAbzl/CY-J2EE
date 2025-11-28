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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/EmployeeListServlet")
public class EmployeeListServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
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

            List<Employee> employees = dao.findAll();

            // Filtrer selon la recherche
            List<Employee> filter1 = new ArrayList<>();

            if (query != null && !query.isBlank()) {
                String q = query.toLowerCase();

                // Construire une liste de chaînes concaténées
                List<String> searchStrings = new ArrayList<>();
                for (Employee e : employees) {
                    String combined = e.getLastName() + " " + e.getFirstName() + " " + e.getLastName() + " " + e.getId();
                    searchStrings.add(combined.toLowerCase()); // pour recherche insensible à la casse
                }

                for (int i = 0; i < employees.size(); i++) {
                    if (searchStrings.get(i).contains(q)) {
                        filter1.add(employees.get(i));
                    }
                }

                req.setAttribute("searchQuery", query);
            }
            else {
                filter1.addAll(employees);
            }

            List<Employee> filter2 = dao.search("", grade, position, dep);

            List<Employee> results = new ArrayList<>();
            for (Employee e1 : filter1){
                for (Employee e2 : filter2){
                    if(e1.getId() == e2.getId()) results.add(e1);
                }
            }


            List<String> grades = dao.findDistinctGrades();
            List<String> positions = dao.findDistinctPositions();
            List<Department> departments = departmentDao.findAll();

            req.setAttribute("employees", results);
            req.setAttribute("grades", grades);
            req.setAttribute("positions", positions);
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("employeesList.jsp").forward(req, resp);
        }
        catch (Exception exception) {
            throw new ServletException(exception);
        }
    }
}