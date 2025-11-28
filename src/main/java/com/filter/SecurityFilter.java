package com.filter;

import com.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * Filtre de sécurité pour vérifier l'authentification et l'autorisation
 */
@WebFilter(urlPatterns = {
    "/EmployeeListServlet",
    "/EmployeeCreateServlet",
    "/EmployeeEditServlet",
    "/EmployeeDeleteServlet",
    "/GeneratePayslipServlet",
    "/PayslipListServlet",
    "/PayslipCreateServlet",
    "/PayslipPrintServlet",
    "/ProjectsListServlet",
    "/ProjectCreateServlet",
    "/ProjectEditServlet",
    "/ProjectDeleteServlet",
    "/ProjectAssignmentServlet",
    "/ProjectMembersServlet",
    "/DepartmentsListServlet",
    "/DepartmentCreateServlet",
    "/DepartmentEditServlet",
    "/DepartmentDeleteServlet",
    "/addEmployee.jsp",
    "/addProject.jsp",
    "/employeesList.jsp",
    "/projectsList.jsp",
    "/dashboard.jsp",
    "/dashboardRH.jsp",
    "/managerDashboard.jsp",
    "/projectDashboard.jsp"
})
public class SecurityFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        // Vérifier si l'utilisateur est authentifié
        User user = null;
        if (session != null) {
            user = (User) session.getAttribute("user");
        }

        if (user == null) {
            // Rediriger vers la page de connexion
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/Login.jsp");
            return;
        }

        // L'utilisateur est authentifié, continuer la chaîne de filtres
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
