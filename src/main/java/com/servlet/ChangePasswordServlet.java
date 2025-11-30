package com.servlet;

import com.dao.UserDAO;
import com.model.Employee;
import com.model.User;
import com.util.DBConnection;

import com.util.PermissionUtil;
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

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req));

        String newMdp = req.getParameter("newMdp");
        String confirmMdp = req.getParameter("confirmMdp");

        if (newMdp == null || !newMdp.equals(confirmMdp)) {
            req.setAttribute("errorMessage", "Les mots de passe ne correspondent pas");
            req.getRequestDispatcher("changePassword.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(false);
        User user = (User) session.getAttribute("user");
        Employee emp = (Employee) session.getAttribute("SESSION_employee");

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDao = new UserDAO(conn);
            userDao.updatePassword(user.getId(), newMdp);
            // Redirection Dashboard selon département
            switch (emp.getDepartmentId()) {
                case 1: // Departement RH
                    resp.sendRedirect("dashboard.jsp");
                    break;
//                    case 2: // DEPT_HEAD
//                        resp.sendRedirect("managerDashboard.jsp");
//                        break;
                default:
                    resp.sendRedirect("EmployeeDashboardServlet");
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
