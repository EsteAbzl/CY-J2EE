package com.servlet;

import java.io.IOException;

import com.dao.EmployeDAO;
import com.model.Employe;
import com.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String mdp = req.getParameter("mdp");

        if (email == null || mdp == null) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp?error=missing");
            return;
        }

        Employe e = employeDAO.findByEmail(email);
        if (e == null) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp?error=notfound");
            return;
        }

        // Verify using BCrypt hashed password
        if (!PasswordUtil.verifyPassword(mdp, e.getMdp())) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp?error=badcreds");
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("employe", e);

        // If first login or default password 'test', force password change
        boolean needChange = Boolean.TRUE.equals(e.getPremiereConnexion()) || "test".equals(e.getMdp());
        if (needChange) {
            resp.sendRedirect(req.getContextPath() + "/changePassword.jsp");
        } else {
            resp.sendRedirect(req.getContextPath() + "/dashboardRH.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/Login.jsp").forward(req, resp);
    }
}
