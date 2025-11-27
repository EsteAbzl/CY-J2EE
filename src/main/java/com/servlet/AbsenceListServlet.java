package com.servlet;

import com.dao.AbsenceDAO;
import com.dao.EmployeeDAO;
import com.model.Absence;
import com.model.Employee;
import com.util.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;

@WebServlet("/AbsenceListServlet")
public class AbsenceListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO employeeDao = new EmployeeDAO(conn);
            AbsenceDAO absenceDao = new AbsenceDAO(conn);

            // Charger tous les employés pour la liste déroulante
            List<Employee> employees = employeeDao.findAll();
            req.setAttribute("employees", employees);

            // Si un employé est sélectionné
            String employeeIdStr = req.getParameter("employeeId");
            if (employeeIdStr != null && !employeeIdStr.isEmpty()) {
                int employeeId = Integer.parseInt(employeeIdStr);
                List<Absence> absences = absenceDao.findByEmployeeId(employeeId);
                req.setAttribute("absences", absences);
                req.setAttribute("selectedEmployeeId", employeeId);
            }

            // Forward vers la JSP
            req.getRequestDispatcher("/absencesList.jsp").forward(req, resp);

        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement des absences", e);
        }
    }
}

