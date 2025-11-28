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
            List<Employee> results = new ArrayList<>();

            // Filtrer selon la recherche
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
                        results.add(employees.get(i));
                    }
                }

                req.setAttribute("searchQuery", query);
            }
            else {
                results.addAll(employees);
            }

            // Appliquer les filtres de grade, position et département
            List<Employee> filtered = new ArrayList<>();
            for (Employee e : results) {
                boolean matchGrade = (grade == null || grade.isBlank() || (e.getGrade() != null && e.getGrade().equals(grade)));
                boolean matchPosition = (position == null || position.isBlank() || (e.getPositionTitle() != null && e.getPositionTitle().equals(position)));
                boolean matchDep = (dep == null || (e.getDepartmentId() != null && e.getDepartmentId().equals(dep)));
                
                if (matchGrade && matchPosition && matchDep) {
                    filtered.add(e);
                }
            }

            List<String> grades = dao.findDistinctGrades();
            List<String> positions = dao.findDistinctPositions();
            List<Department> departments = departmentDao.findAll();

            req.setAttribute("employees", filtered);
            req.setAttribute("grades", grades);
            req.setAttribute("positions", positions);
            req.setAttribute("departments", departments);
            req.setAttribute("grade", grade);
            req.setAttribute("position", position);
            req.setAttribute("department", dep);
            req.getRequestDispatcher("employeesList.jsp").forward(req, resp);
        }
        catch (Exception exception) {
            throw new ServletException(exception);
        }
    }
}