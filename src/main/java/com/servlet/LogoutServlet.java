package com.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;

@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.setAttribute("user", null);
            session.setAttribute("SESSION_employee", null);
            session.setAttribute("emp", null);
            session.setAttribute("SESSION_departementId", null);
            session.setAttribute("SESSION_employeeId", null);

            session.removeAttribute("user");
            session.removeAttribute("SESSION_employee");
            session.removeAttribute("emp");
            session.removeAttribute("SESSION_departementId");
            session.removeAttribute("SESSION_employeeId");

            session.invalidate();
        }
        resp.sendRedirect(req.getContextPath() + "/Logout.jsp");
    }
}