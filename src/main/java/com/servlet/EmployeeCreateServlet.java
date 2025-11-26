package com.servlet;

import com.dao.EmployeeDAO;
import com.dao.UserDAO;
import com.model.User;
import com.model.Employee;
import com.util.DBConnection;
import com.util.ValidationUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/EmployeeCreateServlet")
public class EmployeeCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Récupérer les paramètres du formulaire
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email");
        String grade = req.getParameter("grade");
        String positionTitle = req.getParameter("position_title");
        String baseSalaryStr = req.getParameter("base_salary");
        String departmentIdStr = req.getParameter("department_id");

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);

            Employee emp = new Employee();
            emp.setFirstName(firstName);
            emp.setLastName(lastName);
            emp.setEmail(email);
            emp.setGrade(grade);
            emp.setPositionTitle(positionTitle);
            java.math.BigDecimal salary = new java.math.BigDecimal(baseSalaryStr);
            emp.setBaseSalary(salary.doubleValue());
            if (departmentIdStr == null || departmentIdStr.isBlank()) {
                emp.setDepartmentId(0);
            }
            else {
                emp.setDepartmentId(Integer.parseInt(departmentIdStr));
                emp.setActive(true);
            }

            String originalEmail = emp.getEmail();
            String originalLocal = null;
            String domain = "";
            if (originalEmail != null && originalEmail.contains("@")) {
                int at = originalEmail.indexOf('@');
                originalLocal = originalEmail.substring(0, at);
                domain = originalEmail.substring(at);

                // Préparer une candidate et la rendre unique si nécessaire
                String candidate = originalEmail;
                int attempts = 0;
                while (dao.emailExists(candidate)) {
                    // Ajoute un timestamp pour éviter les collisions
                    candidate = originalLocal + "." + System.currentTimeMillis() + (attempts > 0 ? attempts : "") + domain;
                    attempts++;
                }
                emp.setEmail(candidate);
            }

            // Crée l'employé (EmployeeDAO.create positionne l'ID généré sur emp)
            dao.create(emp);

            // Maintenant on peut mettre l'ID dans l'email final si possible
            if (originalLocal != null && emp.getId() != null) {
                String finalEmail = originalLocal + emp.getId() + domain;
                emp.setEmail(finalEmail);
                dao.update(emp);
            }

            // Créer un compte utilisateur avec mot de passe temporaire "test"
            try {
                UserDAO userDao = new UserDAO(conn);
                User u = new User();
                // username = email
                u.setUsername(emp.getEmail());
                // stocke le mot de passe temporaire tel quel (conforme au schéma existant)
                u.setPasswordHash("test");
                u.setFullName(emp.getFirstName() + " " + emp.getLastName());
                u.setRoleId(4); // EMPLOYEE role
                u.setActive(true);
                u.setEmployeeId(emp.getId());
                u.setMustChangePassword(true);
                userDao.create(u);
            } catch (SQLException ex) {
                // ne pas bloquer la création d'employé si la création du user échoue, mais loguer
                ex.printStackTrace();
            }

            resp.sendRedirect("dashboard.jsp");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de l'ajout d'un employé", e);
        }
    }


    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/employees/form.jsp").forward(req, resp);
    }
}
