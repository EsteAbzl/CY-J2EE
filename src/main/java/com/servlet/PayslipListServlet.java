package com.servlet;

import com.dao.PayslipDAO;
import com.dao.EmployeeDAO;
import com.model.Payslip;
import com.model.Employee;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@WebServlet("/PayslipListServlet")
public class PayslipListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String query = req.getParameter("query");
        String monthStr = req.getParameter("month");
        String yearStr = req.getParameter("year");
        
        Integer month = (monthStr != null && !monthStr.isBlank()) ? Integer.parseInt(monthStr) : null;
        Integer year = (yearStr != null && !yearStr.isBlank()) ? Integer.parseInt(yearStr) : null;

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO payslipDao = new PayslipDAO(conn);
            EmployeeDAO employeeDao = new EmployeeDAO(conn);
            
            List<Payslip> payslips;
            
            // Si une recherche est effectuée
            if (query != null && !query.isBlank()) {
                List<Payslip> allPayslips = payslipDao.findAll();
                payslips = new ArrayList<>();
                String q_lower = query.toLowerCase().trim();
                
                for (Payslip p : allPayslips) {
                    Employee emp = employeeDao.findById(p.getEmployeeId());
                    if (emp != null) {
                        String lastName = emp.getLastName() != null ? emp.getLastName().toLowerCase() : "";
                        String firstName = emp.getFirstName() != null ? emp.getFirstName().toLowerCase() : "";
                        String id = String.valueOf(emp.getId());
                        String fullNameLF = lastName + " " + firstName;
                        String fullNameFL = firstName + " " + lastName;
                        
                        // Cherche si les N premiers caractères correspondent
                        if (lastName.startsWith(q_lower) || 
                            firstName.startsWith(q_lower) || 
                            id.startsWith(q_lower) ||
                            fullNameLF.startsWith(q_lower) ||
                            fullNameFL.startsWith(q_lower)) {
                            payslips.add(p);
                        }
                    }
                }
            } else {
                payslips = payslipDao.findAll();
            }
            
            // Appliquer les filtres de mois et année
            List<Payslip> filtered = new ArrayList<>();
            for (Payslip p : payslips) {
                if ((month == null || month.equals(p.getPeriodMonth())) &&
                    (year == null || year.equals(p.getPeriodYear()))) {
                    filtered.add(p);
                }
            }
            payslips = filtered;
            
            // Crée un map des noms d'employés par ID
            Map<Integer, String> employeeNames = new HashMap<>();
            for (Payslip p : payslips) {
                if (!employeeNames.containsKey(p.getEmployeeId())) {
                    Employee emp = employeeDao.findById(p.getEmployeeId());
                    if (emp != null) {
                        employeeNames.put(p.getEmployeeId(), emp.getLastName() + " " + emp.getFirstName());
                    }
                }
            }

            req.setAttribute("payslips", payslips);
            req.setAttribute("employeeNames", employeeNames);
            req.setAttribute("searchQuery", query);
            req.setAttribute("month", month);
            req.setAttribute("year", year);
            req.getRequestDispatcher("payslipList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des fiches de paie", e);
        }
    }
}


