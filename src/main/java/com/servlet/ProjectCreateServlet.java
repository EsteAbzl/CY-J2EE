package com.servlet;

import com.dao.ProjectDAO;
import com.dao.EmployeeDAO;
import com.model.Project;
import com.model.User;
import com.util.DBConnection;
import com.util.AccessControl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/ProjectCreateServlet")
public class ProjectCreateServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp");
            return;
        }

        // Seuls ADMIN et DEPT_HEAD peuvent créer des projets
        if (!AccessControl.canManageProjects(user)) {
            resp.sendRedirect(req.getContextPath() + "/AccessDeniedServlet");
            return;
        }

        String name = req.getParameter("name");
        String description = req.getParameter("description");
        String status = req.getParameter("status");
        String startDateStr = req.getParameter("start_date");
        String endDateStr = req.getParameter("end_date");
        String deptIdStr = req.getParameter("department_id");

        if (name == null || name.isBlank() || deptIdStr == null || deptIdStr.isBlank()) {
            resp.sendRedirect("addProject.jsp?error=missingFields");
            return;
        }

        try {
            int deptId = Integer.parseInt(deptIdStr);

            // Si DEPT_HEAD, vérifier que c'est son département
            if (AccessControl.isDepartmentHead(user) && user.getEmployeeId() != null) {
                try (Connection conn = DBConnection.getConnection()) {
                    EmployeeDAO empDao = new EmployeeDAO(conn);
                    com.model.Employee headEmp = empDao.findById(user.getEmployeeId());
                    if (headEmp == null || headEmp.getDepartmentId() == null || !headEmp.getDepartmentId().equals(deptId)) {
                        resp.sendRedirect(req.getContextPath() + "/AccessDeniedServlet");
                        return;
                    }
                }
            }

            Project project = new Project();
            project.setName(name);
            project.setDescription(description);
            project.setStatus(status);
            project.setStartDate(java.sql.Date.valueOf(startDateStr));
            project.setEndDate(java.sql.Date.valueOf(endDateStr));
            project.setDepartmentId(deptId);

            try (Connection conn = DBConnection.getConnection()) {
                ProjectDAO dao = new ProjectDAO(conn);
                dao.create(project);
                resp.sendRedirect("ProjectsListServlet?success=create");
            }
        } catch (SQLException e) {
            throw new ServletException("Erreur lors de la création du projet", e);
        }
    }
}
