package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;

import com.dao.ProjectDAO;
import com.dao.EmployeeDAO;
import com.dao.DepartmentDAO;
import com.model.Project;
import com.model.User;
import com.model.Employee;
import com.util.DBConnection;
import com.util.AccessControl;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.sql.Connection;

@WebServlet("/ProjectsListServlet")
public class ProjectsListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Vérifier les droits d'accès
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp");
            return;
        }

        try (Connection conn = DBConnection.getConnection()) {
            ProjectDAO dao = new ProjectDAO(conn);
            EmployeeDAO empDao = new EmployeeDAO(conn);
            DepartmentDAO deptDao = new DepartmentDAO(conn);

            List<Project> projects = dao.findAll();

            // Filtrer selon les droits d'accès
            List<Project> filteredProjects = new ArrayList<>();

            if (AccessControl.isAdmin(user)) {
                // ADMIN : voir tous les projets
                filteredProjects = projects;
            } else if (AccessControl.isDepartmentHead(user) && user.getEmployeeId() != null) {
                // DEPT_HEAD : voir les projets de son département
                Employee headEmp = empDao.findById(user.getEmployeeId());
                if (headEmp != null && headEmp.getDepartmentId() != null) {
                    for (Project p : projects) {
                        if (p.getDepartmentId() != null && p.getDepartmentId().equals(headEmp.getDepartmentId())) {
                            filteredProjects.add(p);
                        }
                    }
                }
            } else if (AccessControl.isProjectHead(user) && user.getEmployeeId() != null) {
                // PROJECT_HEAD : voir les projets où il est chef
                for (Project p : projects) {
                    if (AccessControl.isProjectLeadForProject(user.getEmployeeId(), p.getId(), conn)) {
                        filteredProjects.add(p);
                    }
                }
            } else if (AccessControl.isEmployee(user) && user.getEmployeeId() != null) {
                // EMPLOYEE : voir les projets où il est assigné
                for (Project p : projects) {
                    if (AccessControl.isEmployeeAssignedToProject(user.getEmployeeId(), p.getId(), conn)) {
                        filteredProjects.add(p);
                    }
                }
            }

            // Crée un map des noms de départements
            java.util.Map<Integer, String> departmentNames = new java.util.HashMap<>();
            for (Project p : filteredProjects) {
                if (p.getDepartmentId() != null && !departmentNames.containsKey(p.getDepartmentId())) {
                    com.model.Department dept = deptDao.findById(p.getDepartmentId());
                    if (dept != null) {
                        departmentNames.put(p.getDepartmentId(), dept.getName());
                    }
                }
            }

            req.setAttribute("projects", filteredProjects);
            req.setAttribute("departmentNames", departmentNames);
            req.setAttribute("isAdmin", AccessControl.isAdmin(user));
            req.setAttribute("isDeptHead", AccessControl.isDepartmentHead(user));
            req.setAttribute("isProjectHead", AccessControl.isProjectHead(user));
            req.getRequestDispatcher("projectsList.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException("Erreur lors du chargement des projets", e);
        }
    }
}
