package com.servlet;

import com.dao.PayslipDAO;
import com.dao.EmployeeDAO;
import com.dao.SalaireExtraDAO;
import com.model.Payslip;
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
import java.util.List;


@WebServlet("/PayslipPrintServlet")
public class PayslipPrintServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String idStr = req.getParameter("id");

        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("payslipList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            PayslipDAO payslipDao = new PayslipDAO(conn);
            EmployeeDAO employeeDao = new EmployeeDAO(conn);
            SalaireExtraDAO extraDao = new SalaireExtraDAO(conn);
            
            Payslip payslip = payslipDao.findById(id);

            if (payslip == null) {
                resp.sendRedirect("payslipList.jsp?error=notFound");
                return;
            }

            PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}, new Integer[] {payslip.getEmployeeId()}));


            Employee employee = employeeDao.findById(payslip.getEmployeeId());
            List<SalaireExtra> extras = extraDao.findByEmployeeAndPeriod(payslip.getEmployeeId(), payslip.getPeriodYear(), payslip.getPeriodMonth());

            req.setAttribute("payslip", payslip);
            req.setAttribute("employee", employee);
            req.setAttribute("extras", extras);
            req.getRequestDispatcher("payslipPrint.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement de la fiche de paie", e);
        }
    }
}

