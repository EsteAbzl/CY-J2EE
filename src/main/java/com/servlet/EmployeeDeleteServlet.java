package com.servlet;

import com.dao.EmployeeDAO;
import com.model.Employee;
import com.util.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/EmployeeDeleteServlet")
public class EmployeeDeleteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String idStr = req.getParameter("id");
        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("EmployeeListServlet?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            Employee employee = dao.findById(id);

            if (employee != null) {
                // Suppression logique: marquer comme inactif
                employee.setActive(false);
                dao.update(employee);
                resp.sendRedirect("EmployeeListServlet");
            } else {
                resp.sendRedirect("EmployeeListServlet?error=notFound");
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de la suppression de l'employé", e);
        }
    }
}


