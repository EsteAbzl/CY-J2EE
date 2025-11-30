package com.servlet;

import com.dao.DepartmentDAO;
import com.model.Department;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/addEmployeeServlet")
public class AddEmployeeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO departmentDao = new DepartmentDAO(conn);
            List<Department> departments = departmentDao.findAll();

            req.setAttribute("departments", departments);

        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des départements", e);
        }

        req.getRequestDispatcher("addEmployee.jsp").forward(req, resp);
    }
}
