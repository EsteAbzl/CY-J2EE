package com.servlet;

import com.dao.*;
import com.model.*;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/PayslipCreateServlet")
public class PayslipCreateServlet extends HttpServlet {
    private double calculateDeductions(Employee e, int year, int month, Connection conn) {
        return 0.0;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));
        int month = Integer.parseInt(req.getParameter("period_month"));
        int year = Integer.parseInt(req.getParameter("period_year"));

        // ⚠️ Récupération des champs saisis
        double baseSalary = Double.parseDouble(req.getParameter("base_salary"));
        double bonuses = Double.parseDouble(req.getParameter("bonuses"));
        double deductions = Double.parseDouble(req.getParameter("deductions"));
        double netPay = baseSalary + bonuses - deductions;

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            Employee emp = employeeDAO.findById(employeeId);

            if (emp == null) {
                resp.sendRedirect("payslipList.jsp?error=employeeNotFound");
                return;
            }

            Payslip payslip = new Payslip();
            payslip.setEmployeeId(employeeId);
            payslip.setPeriodMonth(month);
            payslip.setPeriodYear(year);
            payslip.setBaseSalary(baseSalary);   //  valeur saisie
            payslip.setBonuses(bonuses);         //  valeur saisie
            payslip.setDeductions(deductions);   //  valeur saisie
            payslip.setNetPay(netPay);           //  calculé
            payslip.setGeneratedAt(new java.sql.Timestamp(System.currentTimeMillis()));

            PayslipDAO payslipDAO = new PayslipDAO(conn);
            payslipDAO.create(payslip);

            resp.sendRedirect("dashboard.jsp?success=create");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la génération de la fiche de paie", e);
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/payslips/list.jsp").forward(req, resp);
    }
}