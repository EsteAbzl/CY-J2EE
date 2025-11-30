package com.servlet;

import com.dao.SalaireDAO;
import com.model.Salaire;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/SalaireServlet")
public class SalaireServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req));

        try (Connection conn = DBConnection.getConnection()) {
            SalaireDAO salaireDAO = new SalaireDAO(conn);
            List<Salaire> salaires = salaireDAO.findAll();
            req.setAttribute("salaires", salaires);
            req.getRequestDispatcher("/salaire.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la récupération des salaires", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int employeeId = Integer.parseInt(req.getParameter("employee_id"));
        double salary = Double.parseDouble(req.getParameter("salary"));
        java.sql.Date date = java.sql.Date.valueOf(req.getParameter("date"));

        try (Connection conn = DBConnection.getConnection()) {
            Salaire salaire = new Salaire();
            salaire.setEmployeeId(employeeId);
            salaire.setSalaire(salary);
            salaire.setDate(date);

            SalaireDAO salaireDAO = new SalaireDAO(conn);
            salaireDAO.create(salaire);

            resp.sendRedirect("SalaireServlet?success=salairCreated");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la création du salaire", e);
        }
    }
}
