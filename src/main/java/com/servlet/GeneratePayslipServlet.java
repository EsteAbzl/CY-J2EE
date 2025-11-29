package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/GeneratePayslipServlet")
public class GeneratePayslipServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String employeeId = req.getParameter("id");
        if (employeeId == null || employeeId.isBlank()) {
            resp.sendRedirect("EmployeeListServlet?error=missingId");
            return;
        }
        
        // Redirection vers generatePayslip.jsp avec l'ID de l'employé
        req.setAttribute("employee_id", employeeId);
        req.getRequestDispatcher("generatePayslip.jsp").forward(req, resp);
    }
}
