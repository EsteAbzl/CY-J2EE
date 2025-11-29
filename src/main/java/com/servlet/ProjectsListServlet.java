package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.dao.ProjectDAO;
import com.dao.DepartmentDAO;
import com.model.Project;
import com.model.Department;
import com.util.DBConnection;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;

@WebServlet("/ProjectsListServlet")
public class ProjectsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn);
            DepartmentDAO deptDao = new DepartmentDAO(conn);
            List<Project> projects = dao.findAll();

            // Créer une map des noms de départements par ID
            Map<Integer, String> departmentNames = new HashMap<>();
            List<Department> departments = deptDao.findAll();
            for (Department d : departments) {
                departmentNames.put(d.getId(), d.getName());
            }

            req.setAttribute("projects", projects);
            req.setAttribute("departmentNames", departmentNames);
            req.getRequestDispatcher("projectsList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des projets", e);
        }
    }
}
