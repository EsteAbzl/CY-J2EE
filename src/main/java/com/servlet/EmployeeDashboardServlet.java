package com.servlet;

import com.dao.PayslipDAO;
import com.model.Employee;
import com.model.Payslip;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/EmployeeDashboardServlet")
public class EmployeeDashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        Employee emp = (Employee) (session != null ? session.getAttribute("emp") : null);

        if (emp == null) {
            resp.sendRedirect("login.jsp?error=sessionExpired");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO payslipDao = new PayslipDAO(conn);
            List<Payslip> payslips = payslipDao.findByEmployeeId(emp.getId());

            req.setAttribute("employee", emp);
            req.setAttribute("payslips", payslips);

            req.getRequestDispatcher("/employeeDashboard.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement du dashboard employé", e);
        }
    }
}

