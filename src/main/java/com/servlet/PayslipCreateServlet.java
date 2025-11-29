package com.servlet;

import com.dao.*;
import com.model.*;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

@WebServlet("/PayslipCreateServlet")
public class PayslipCreateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));
        Date monthDate = Date.valueOf(req.getParameter("period_date")+"-27");
        int year = monthDate.getYear()+1900;
        int month = monthDate.getMonth()+1;

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            Employee emp = employeeDAO.findById(employeeId);

            if (emp == null) {
                resp.sendRedirect("generatePayslip.jsp?error=employeeNotFound");
                return;
            }

            // Récupérer le salaire le plus proche dans le passé
            SalaireDAO salaireDAO = new SalaireDAO(conn);
            // Créer une date de référence pour le dernier jour du mois
            java.sql.Date referenceDate = java.sql.Date.valueOf(year + "-" + String.format("%02d", month) + "-27");
            Salaire salaire = salaireDAO.findMostRecentSalaire(employeeId, referenceDate);

            if (salaire == null) {
                // Si aucun salaire trouvé, rediriger vers la liste des fiches de paie avec un message d'erreur
                resp.sendRedirect("PayslipListServlet?error=noSalaire");
                return;
            }

            // Récupérer tous les salaires extras pour ce mois et cette année
            SalaireExtraDAO salaireExtraDAO = new SalaireExtraDAO(conn);
            List<SalaireExtra> salairesExtra = salaireExtraDAO.findByEmployeeAndPeriod(employeeId, year, month);

            // Calculer les primes et déductions séparément
            double bonuses = 0;
            double deductions = 0;
            for (SalaireExtra se : salairesExtra) {
                if (se.getMontant() > 0) {
                    bonuses += se.getMontant(); // Primes (montants positifs)
                } else {
                    deductions += Math.abs(se.getMontant()); // Déductions (montants négatifs)
                }
            }

            // Calculer le salaire net
            double baseSalary = salaire.getSalaire();
            double netPay = baseSalary + bonuses - deductions;

            // Créer la fiche de paie
            Payslip payslip = new Payslip();
            payslip.setEmployeeId(employeeId);
            payslip.setPeriodMonth(month);
            payslip.setPeriodYear(year);
            payslip.setBaseSalary(baseSalary);
            payslip.setBonuses(bonuses);
            payslip.setDeductions(deductions);
            payslip.setNetPay(netPay);
            payslip.setGeneratedAt(new java.sql.Timestamp(System.currentTimeMillis()));

            PayslipDAO payslipDAO = new PayslipDAO(conn);
            
            // Vérifier si une fiche de paie existe déjà pour cette période
            List<Payslip> existingPayslips = payslipDAO.findFiltered(employeeId, month, year);
            
            if (!existingPayslips.isEmpty()) {
                // Supprimer la fiche existante pour la recréer
                Payslip existingPayslip = existingPayslips.get(0);
                // On met à jour l'ID pour garder le même enregistrement
                payslip.setId(existingPayslip.getId());
                // Supprimer et recréer
                String deleteSql = "DELETE FROM payslips WHERE id = ?";
                try (java.sql.PreparedStatement ps = conn.prepareStatement(deleteSql)) {
                    ps.setInt(1, existingPayslip.getId());
                    ps.executeUpdate();
                }
            }
            
            // Créer la nouvelle fiche
            payslipDAO.create(payslip);

            resp.sendRedirect("PayslipListServlet?success=payslipCreated");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la génération de la fiche de paie", e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/generatePayslip.jsp").forward(req, resp);
    }
}
