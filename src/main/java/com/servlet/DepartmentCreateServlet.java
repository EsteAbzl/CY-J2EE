package com.servlet;

import com.dao.DepartmentDAO;
import com.model.Department;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/DepartmentCreateServlet")
public class DepartmentCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        String name = req.getParameter("name");
        String description = req.getParameter("description");

        if (name == null || name.isBlank()) {
            req.setAttribute("errorMessage", "Le nom du département est obligatoire.");
            req.getRequestDispatcher("addDepartment.jsp").forward(req, resp);
            return;
        }

        Department dept = new Department();
        dept.setName(name);
        dept.setDescription(description);

        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO dao = new DepartmentDAO(conn);
            dao.create(dept);
            resp.sendRedirect("dashboard.jsp?success=add");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de l'ajout du département", e);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("addDepartment.jsp").forward(req, resp);
    }
}

