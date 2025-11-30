package com.servlet;

import com.dao.EmployeeDAO;
import com.dao.SalaireExtraDAO;
import com.model.Employee;
import com.model.SalaireExtra;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/UpdateSalaryServlet")
public class UpdateSalaryServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String employeeIdStr = req.getParameter("employee_id");
        String dateStr = req.getParameter("date");
        String extraStr = req.getParameter("extra");
        String description = req.getParameter("description");

        // Validation des paramètres obligatoires
        if (employeeIdStr == null || dateStr == null || extraStr == null || 
            employeeIdStr.isBlank() || dateStr.isBlank() || extraStr.isBlank()) {
            resp.sendRedirect("updateSalary.jsp?error=missing");
            return;
        }

        try {
            int employeeId = Integer.parseInt(employeeIdStr);
            java.sql.Date date = java.sql.Date.valueOf(dateStr);
            double extra = Double.parseDouble(extraStr);

            try (Connection conn = DBConnection.getConnection()) {
                // Vérifier que l'employé existe
                EmployeeDAO employeeDAO = new EmployeeDAO(conn);
                Employee emp = employeeDAO.findById(employeeId);
                
                if (emp == null) {
                    resp.sendRedirect("updateSalary.jsp?error=employeeNotFound&id=" + employeeId);
                    return;
                }

                // Ajouter le salaire_extra
                SalaireExtra salaireExtra = new SalaireExtra();
                salaireExtra.setEmployeeId(employeeId);
                salaireExtra.setMontant(extra);
                salaireExtra.setMotif(description != null && !description.isBlank() ? description : "");
                salaireExtra.setDate(date);
                
                SalaireExtraDAO salaireExtraDAO = new SalaireExtraDAO(conn);
                salaireExtraDAO.create(salaireExtra);

                resp.sendRedirect("EmployeeListServlet?success=extraAdded");
            } catch (SQLException e) {
                throw new ServletException("Erreur lors de l'ajout de l'extra", e);
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect("updateSalary.jsp?error=invalidFormat");
        }
    }
}
