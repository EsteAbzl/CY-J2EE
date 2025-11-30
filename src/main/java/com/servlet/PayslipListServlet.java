package com.servlet;

import com.dao.PayslipDAO;
import com.dao.EmployeeDAO;
import com.model.Payslip;
import com.model.Employee;
import com.util.DBConnection;
import com.util.PermissionUtil;
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
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String query = req.getParameter("query");
        String monthStr = req.getParameter("month");
        String yearStr = req.getParameter("year");

        Integer month = (monthStr != null && !monthStr.isBlank()) ? Integer.parseInt(monthStr) : null;
        Integer year = (yearStr != null && !yearStr.isBlank()) ? Integer.parseInt(yearStr) : null;

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO payslipDao = new PayslipDAO(conn);
            EmployeeDAO employeeDao = new EmployeeDAO(conn);

            List<Payslip> payslips = payslipDao.findAll();

            // Filtrer selon la recherche
            List<Payslip> filter1 = new ArrayList<>();

            if (query != null && !query.isBlank()) {
                String q = query.toLowerCase();

                // Construire une liste de chaînes concaténées
                List<String> searchStrings = new ArrayList<>();
                for (Payslip p : payslips) {
                    Employee e = employeeDao.findById(p.getEmployeeId());
                    String combined = e.getLastName() + " " + e.getFirstName() + " " + e.getLastName() + " " + e.getId();
                    searchStrings.add(combined.toLowerCase()); // pour recherche insensible à la casse
                }

                for (int i = 0; i < payslips.size(); i++) {
                    if (searchStrings.get(i).contains(q)) {
                        filter1.add(payslips.get(i));
                    }
                }

                req.setAttribute("searchQuery", query);
            }
            else {
                filter1.addAll(payslips);
            }

            // Appliquer les filtres de mois et année
            List<Payslip> filter2 = new ArrayList<>();
            for (Payslip p : filter1) {
                if ((month == null || month.equals(p.getPeriodMonth())) &&
                    (year == null || year.equals(p.getPeriodYear()))) {
                    filter2.add(p);
                }
            }


            // Crée un map des noms d'employés par ID
            Map<Integer, String> employeeNames = new HashMap<>();
            for (Payslip p : filter2) {
                if (!employeeNames.containsKey(p.getEmployeeId())) {
                    Employee emp = employeeDao.findById(p.getEmployeeId());
                    if (emp != null) {
                        employeeNames.put(p.getEmployeeId(), emp.getLastName() + " " + emp.getFirstName());
                    }
                }
            }

            req.setAttribute("payslips", filter2);
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


