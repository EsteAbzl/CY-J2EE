package com.servlet;

import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/DepartmentMembersServlet")
public class DepartmentMembersServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req));

        String deptIdStr = req.getParameter("id");

        if (deptIdStr == null || deptIdStr.isBlank()) {
            resp.sendRedirect("departmentsList.jsp?error=missingId");
            return;
        }

        int deptId = Integer.parseInt(deptIdStr);

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn); // passe la connexion
            List<Employee> employees = dao.searchByDepartment(deptId);

            req.setAttribute("employees", employees);
            req.getRequestDispatcher("departmentMembers.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des membres du département", e);
        }
    }
}
