package com.servlet;

import com.dao.EmployeeDAO;
import com.util.DBConnection;
import com.util.HibernateUtil;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

@WebServlet("/GeneratePayslipServlet")
public class GeneratePayslipServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String employeeId = req.getParameter("id");
        if (employeeId == null || employeeId.isBlank()) {
            resp.sendRedirect("EmployeeListServlet?error=missingId");
            return;
        }

        try(Connection conn = DBConnection.getConnection()){
            EmployeeDAO employeeDAO = new EmployeeDAO(conn);
            Date dateArriveEmployee = employeeDAO.arrivingDate(Integer.valueOf(employeeId));
            req.setAttribute("dateArriveEmployee", (dateArriveEmployee.getYear()+1900) + "-" + String.format("%02d", (dateArriveEmployee.getMonth()+1)));

        }
        catch (Exception exception) {
            throw new ServletException(exception);
        }

        // Redirection vers generatePayslip.jsp avec l'ID de l'employé
        req.setAttribute("employee_id", employeeId);
        req.getRequestDispatcher("generatePayslip.jsp").forward(req, resp);
    }
}
