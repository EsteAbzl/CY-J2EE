package com.servlet;

import com.dao.SalaireExtraDAO;
import com.model.SalaireExtra;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/SalaireExtraServlet")
public class SalaireExtraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            SalaireExtraDAO salaireExtraDAO = new SalaireExtraDAO(conn);
            List<SalaireExtra> salairesExtra = salaireExtraDAO.findAll();
            req.setAttribute("salairesExtra", salairesExtra);
            req.getRequestDispatcher("/salaireExtra.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des salaires extras", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));
        double montant = Double.parseDouble(req.getParameter("montant"));
        String motif = req.getParameter("motif");
        java.sql.Date date = java.sql.Date.valueOf(req.getParameter("date"));

        try (Connection conn = DBConnection.getConnection()) {
            SalaireExtra salaireExtra = new SalaireExtra();
            salaireExtra.setEmployeeId(employeeId);
            salaireExtra.setMontant(montant);
            salaireExtra.setMotif(motif);
            salaireExtra.setDate(date);

            SalaireExtraDAO salaireExtraDAO = new SalaireExtraDAO(conn);
            salaireExtraDAO.create(salaireExtra);

            resp.sendRedirect("SalaireExtraServlet?success=salaireExtraCreated");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la création du salaire extra", e);
        }
    }
}
