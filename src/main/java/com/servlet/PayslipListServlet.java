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
import java.util.List;


@WebServlet("/PayslipListServlet")
public class PayslipListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO dao = new PayslipDAO(conn);
            List<Payslip> payslips = dao.findAll();

            req.setAttribute("payslips", payslips);
            req.getRequestDispatcher("payslipList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des fiches de paie", e);
        }
    }
}

