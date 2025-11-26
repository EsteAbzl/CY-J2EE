package com.servlet;

import com.dao.UserDAO;
import com.model.User;
import com.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String newMdp = req.getParameter("newMdp");
        String confirmMdp = req.getParameter("confirmMdp");

        if (newMdp == null || !newMdp.equals(confirmMdp)) {
            req.setAttribute("errorMessage", "Les mots de passe ne correspondent pas");
            req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (user == null) {
            resp.sendRedirect("Login.jsp");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDao = new UserDAO(conn);
            userDao.updatePassword(user.getId(), newMdp);
            // Après changement, rediriger selon rôle
            switch (user.getRoleId()) {
                case 1:
                    resp.sendRedirect("dashboard.jsp");
                    break;
                case 2:
                    resp.sendRedirect("managerDashboard.jsp");
                    break;
                case 3:
                    resp.sendRedirect("projectDashboard.jsp");
                    break;
                case 4:
                    resp.sendRedirect("employeeDashboard.jsp");
                    break;
                default:
                    resp.sendRedirect("error.jsp");
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors du changement de mot de passe", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
    }
}
