package com.servlet;

import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            List<Employee> employees = dao.findAll();

            // Construire une liste de chaînes concaténées
            List<String> searchStrings = new ArrayList<>();
            for (Employee e : employees) {
                String combined = e.getLastName() + " " + e.getFirstName() + " " + e.getId();
                searchStrings.add(combined.toLowerCase()); // pour recherche insensible à la casse
            }

            // Filtrer selon la recherche
            List<Employee> results = new ArrayList<>();
            if (query != null && !query.isBlank()) {
                String q = query.toLowerCase();
                for (int i = 0; i < employees.size(); i++) {
                    if (searchStrings.get(i).contains(q)) {
                        results.add(employees.get(i));
                    }
                }
            }

            req.setAttribute("results", results);
            req.setAttribute("searchQuery", query);
            req.getRequestDispatcher("searchResults.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la recherche", e);
        }
    }
}
