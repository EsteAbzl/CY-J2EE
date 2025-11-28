package com.servlet;

import com.dao.ProjectDAO;
import com.dao.DepartmentDAO;
import com.model.Project;
import com.model.Department;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ProjectCreateServlet")
public class ProjectCreateServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnection.getConnection()) {
            DepartmentDAO departmentDao = new DepartmentDAO(conn);
            List<Department> departments = departmentDao.findAll();
            req.setAttribute("departments", departments);
            req.getRequestDispatcher("addProject.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des départements", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String status = req.getParameter("status");
        String startDateStr = req.getParameter("start_date");
        String endDateStr = req.getParameter("end_date");
        String deptIdStr = req.getParameter("department_id");

        if (name == null || name.isBlank() || deptIdStr == null || deptIdStr.isBlank()) {
            resp.sendRedirect("projectsList.jsp?error=missingFields");
            return;
        }

        Project project = new Project();
        project.setName(name);
        project.setDescription(description);
        project.setStatus(status);
        project.setStartDate(java.sql.Date.valueOf(startDateStr));
        project.setEndDate(java.sql.Date.valueOf(endDateStr));
        project.setDepartmentId(Integer.parseInt(deptIdStr));

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn);
            dao.create(project);
            resp.sendRedirect("ProjectsListServlet");
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la création du projet", e);
        }
    }
}

