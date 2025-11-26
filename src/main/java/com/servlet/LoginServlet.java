package com.servlet;

import com.dao.UserDAO;
import com.dao.EmployeeDAO;
import com.model.User;
import com.model.Employee;
import com.util.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        try (Connection conn = DBConnection.getConnection()) {
            UserDAO userDao = new UserDAO(conn);
            User user = userDao.findByCredentials(username, password);

            if (user != null) {
                HttpSession session = req.getSession();
                session.setAttribute("user", user);

                // Charger l'employé lié
                EmployeeDAO empDao = new EmployeeDAO(conn);
                Employee emp = empDao.findById(user.getEmployeeId());
                session.setAttribute("emp", emp);
                // some JSPs expect attribute name 'employe'
                session.setAttribute("employe", emp);
                session.setAttribute("employeeId", user.getEmployeeId());
                // Si le flag mustChangePassword est présent et vrai, ou si le mot de passe est "test",
                // forcer l'utilisateur à changer son mot de passe.
                boolean mustChange = false;
                try { mustChange = user.isMustChangePassword(); } catch (Throwable ignore) { }
                if (mustChange || "test".equals(password)) {
                    // rediriger vers la page de changement de mot de passe
                    resp.sendRedirect("changePassword.jsp");
                    return;
                }

                // Redirection selon rôle
                switch (user.getRoleId()) {
                    case 1: // ADMIN
                        resp.sendRedirect("dashboard.jsp");
                        break;
                    case 2: // DEPT_HEAD
                        resp.sendRedirect("managerDashboard.jsp");
                        break;
                    case 3: // PROJECT_HEAD
                        resp.sendRedirect("projectDashboard.jsp");
                        break;
                    case 4: // EMPLOYEE
                        resp.sendRedirect("employeeDashboard.jsp");
                        break;
                    default:
                        resp.sendRedirect("error.jsp");
                        break;
                }
            } else {
                // Identifiants invalides
                req.setAttribute("errorMessage", "Nom d'utilisateur ou mot de passe incorrect");
                req.getRequestDispatcher("Login.jsp").forward(req, resp);
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL", e);
        }
    }
}
