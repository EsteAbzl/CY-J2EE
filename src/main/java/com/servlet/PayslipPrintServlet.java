package com.servlet;

import com.dao.PayslipDAO;
import com.model.Payslip;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/PayslipPrintServlet")
public class PayslipPrintServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("payslipList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO dao = new PayslipDAO(conn); // passe la connexion
            Payslip payslip = dao.findById(id);

            if (payslip == null) {
                resp.sendRedirect("payslipList.jsp?error=notFound");
                return;
            }

            req.setAttribute("payslip", payslip);
            req.getRequestDispatcher("payslipPrint.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement de la fiche de paie", e);
        }
    }
}

