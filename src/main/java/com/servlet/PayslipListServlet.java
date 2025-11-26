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
        String employeeIdStr = req.getParameter("employee_id");
        String monthStr = req.getParameter("month");
        String yearStr = req.getParameter("year");

        Integer employeeId = (employeeIdStr != null && !employeeIdStr.isBlank()) ? Integer.parseInt(employeeIdStr) : null;
        Integer month = (monthStr != null && !monthStr.isBlank()) ? Integer.parseInt(monthStr) : null;
        Integer year = (yearStr != null && !yearStr.isBlank()) ? Integer.parseInt(yearStr) : null;

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO dao = new PayslipDAO(conn);
            List<Payslip> payslips = dao.findFiltered(employeeId, month, year);

            req.setAttribute("payslips", payslips);
            req.getRequestDispatcher("payslipList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des fiches de paie", e);
        }
    }
}


