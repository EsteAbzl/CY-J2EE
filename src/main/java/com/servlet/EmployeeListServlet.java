package com.servlet;

import com.dao.EmployeeDAO;
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

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            List<Employee> employees = dao.findAll();
            
            // Si une recherche est effectuée
            if (query != null && !query.isBlank()) {
                List<Employee> results = new ArrayList<>();
                String q_lower = query.toLowerCase().trim();
                
                for (Employee e : employees) {
                    String lastName = e.getLastName() != null ? e.getLastName().toLowerCase() : "";
                    String firstName = e.getFirstName() != null ? e.getFirstName().toLowerCase() : "";
                    String id = String.valueOf(e.getId());
                    String fullNameLF = lastName + " " + firstName; // Nom Prénom
                    String fullNameFL = firstName + " " + lastName; // Prénom Nom
                    
                    // Cherche si les N premiers caractères correspondent
                    if (lastName.startsWith(q_lower) || 
                        firstName.startsWith(q_lower) || 
                        id.startsWith(q_lower) ||
                        fullNameLF.startsWith(q_lower) ||
                        fullNameFL.startsWith(q_lower)) {
                        results.add(e);
                    }
                }
                
                req.setAttribute("employees", results);
                req.setAttribute("searchQuery", query);
            } else {
                req.setAttribute("employees", employees);
            }
            
            req.getRequestDispatcher("employeesList.jsp").forward(req, resp);
        }
        catch (Exception e) {
            throw new ServletException(e);
        }
    }
}