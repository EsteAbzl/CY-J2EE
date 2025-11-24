package com.servlet;

import com.dao.DepartmentDAO;
import com.model.Department;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;


@WebServlet("/DepartmentEditServlet")
public class DepartmentEditServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStr = req.getParameter("id");

        if (idStr == null || idStr.isBlank()) {
            resp.sendRedirect("departmentsList.jsp?error=missingId");
            return;
        }

        int id = Integer.parseInt(idStr);

        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO dao = new DepartmentDAO(conn); // passe la connexion
            Department dept = dao.findById(id);

            if (dept == null) {
                resp.sendRedirect("departmentsList.jsp?error=notFound");
                return;
            }

            req.setAttribute("department", dept);
            req.getRequestDispatcher("editDepartment.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement du département", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String description= req.getParameter("description");

        Department dept = new Department();
        dept.setId(id);
        dept.setName(name);
        dept.setDescription(description);

        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO dao = new DepartmentDAO(conn);
            dao.update(dept);
            resp.sendRedirect("dashboard.jsp?success=update");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la mise à jour du département", e);
        }
    }
}

