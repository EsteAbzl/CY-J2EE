package com.servlet;

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/GeneratePayslipServlet")
public class GeneratePayslipServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String employee = request.getParameter("employee");
        String month = request.getParameter("month");
        int year = Integer.parseInt(request.getParameter("year"));
        double baseSalary = Double.parseDouble(request.getParameter("baseSalary"));
        double bonus = Double.parseDouble(request.getParameter("bonus"));
        double deductions = Double.parseDouble(request.getParameter("deductions"));

        double netSalary = baseSalary + bonus - deductions;

        request.setAttribute("employee", employee);
        request.setAttribute("month", month);
        request.setAttribute("year", year);
        request.setAttribute("baseSalary", baseSalary);
        request.setAttribute("bonus", bonus);
        request.setAttribute("deductions", deductions);
        request.setAttribute("netSalary", netSalary);

        RequestDispatcher dispatcher = request.getRequestDispatcher("payslipPreview.jsp");
        dispatcher.forward(request, response);
    }
}
