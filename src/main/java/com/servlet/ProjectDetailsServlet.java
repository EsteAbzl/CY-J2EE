package com.servlet;

import com.dao.ProjectDAO;
import com.dao.EmployeeDAO;
import com.dao.DepartmentDAO;
import com.model.Project;
import com.model.Employee;
import com.model.User;
import com.util.DBConnection;
import com.util.AccessControl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ProjectDetailsServlet")
public class ProjectDetailsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        User user = (User) (session != null ? session.getAttribute("user") : null);

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp");
            return;
        }

        String projectIdStr = req.getParameter("id");
        if (projectIdStr == null || projectIdStr.isBlank()) {
            resp.sendRedirect(req.getContextPath() + "/ProjectsListServlet?error=missingId");
            return;
        }

        try {
            int projectId = Integer.parseInt(projectIdStr);
            try (Connection conn = DBConnection.getConnection()) {
                // Vérifier l'accès au projet
                if (!AccessControl.canAccessProject(user, projectId, conn)) {
                    resp.sendRedirect(req.getContextPath() + "/AccessDeniedServlet");
                    return;
                }

                ProjectDAO projectDao = new ProjectDAO(conn);
                DepartmentDAO deptDao = new DepartmentDAO(conn);
                EmployeeDAO empDao = new EmployeeDAO(conn);

                Project project = projectDao.findById(projectId);
                if (project == null) {
                    resp.sendRedirect(req.getContextPath() + "/ProjectsListServlet?error=notFound");
                    return;
                }

                // Charger le nom du département
                String departmentName = "";
                if (project.getDepartmentId() != null) {
                    com.model.Department dept = deptDao.findById(project.getDepartmentId());
                    if (dept != null) {
                        departmentName = dept.getName();
                    }
                }

                // Charger les membres du projet
                List<Employee> members = projectDao.findMembers(projectId);

                req.setAttribute("project", project);
                req.setAttribute("departmentName", departmentName);
                req.setAttribute("members", members);
                req.setAttribute("isAdmin", AccessControl.isAdmin(user));
                req.setAttribute("isDeptHead", AccessControl.isDepartmentHead(user));
                req.setAttribute("isProjectHead", AccessControl.isProjectHead(user));
                req.setAttribute("canManage", AccessControl.isAdmin(user) || 
                    (AccessControl.isDepartmentHead(user) && user.getEmployeeId() != null && empDao.findById(user.getEmployeeId()).getDepartmentId().equals(project.getDepartmentId())) ||
                    (AccessControl.isProjectHead(user) && AccessControl.isProjectLeadForProject(user.getEmployeeId(), projectId, conn)));

                req.getRequestDispatcher("projectDetails.jsp").forward(req, resp);
            }
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/ProjectsListServlet?error=invalid");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL", e);
        }
    }
}
