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
import java.util.List;
import java.util.Map;
import java.util.HashMap;


@WebServlet("/PayslipListServlet")
public class PayslipListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeIdStr = req.getParameter("employee_id");
        String monthStr = req.getParameter("month");
        String yearStr = req.getParameter("year");

        Integer employeeId = (employeeIdStr != null && !employeeIdStr.isBlank()) ? Integer.parseInt(employeeIdStr) : null;
        Integer month = (monthStr != null && !monthStr.isBlank()) ? Integer.parseInt(monthStr) : null;
        Integer year = (yearStr != null && !yearStr.isBlank()) ? Integer.parseInt(yearStr) : null;

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO payslipDao = new PayslipDAO(conn);
            EmployeeDAO employeeDao = new EmployeeDAO(conn);
            
            List<Payslip> payslips = payslipDao.findFiltered(employeeId, month, year);
            
            // Crée un map des noms d'employés par ID
            Map<Integer, String> employeeNames = new HashMap<>();
            for (Payslip p : payslips) {
                if (!employeeNames.containsKey(p.getEmployeeId())) {
                    Employee emp = employeeDao.findById(p.getEmployeeId());
                    if (emp != null) {
                        employeeNames.put(p.getEmployeeId(), emp.getFirstName() + " " + emp.getLastName());
                    }
                }
            }

            req.setAttribute("payslips", payslips);
            req.setAttribute("employeeNames", employeeNames);
            req.getRequestDispatcher("payslipList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des fiches de paie", e);
        }
    }
}


