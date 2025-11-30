package com.servlet;

import com.dao.AbsenceDAO;
import com.model.Absence;
import com.model.Employee;
import com.util.DBConnection;
import com.util.PermissionUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/AbsenceServlet")
public class AbsenceServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req,new Integer[] {1}));

        HttpSession session = req.getSession(false);
        int employeeId = (int)session.getAttribute("SESSION_employeeId");

        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req,new Integer[] {1}, new Integer[] {employeeId}));

        try (Connection conn = DBConnection.getConnection()) {
            AbsenceDAO dao = new AbsenceDAO(conn);
            List<Absence> absences = dao.findByEmployeeId(employeeId);

            req.setAttribute("absences", absences);
            req.getRequestDispatcher("/absences.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException("Erreur lors du chargement des absences", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        Employee emp = (Employee) (session != null ? session.getAttribute("SESSION_employee") : null);

        if (emp == null) {
            resp.sendRedirect("login.jsp?error=sessionExpired");
            return;
        }

        int employeeId = emp.getId();

        // Récupérer les paramètres du formulaire
        String dateStr = req.getParameter("date");
        String type = req.getParameter("type");
        String hoursStr = req.getParameter("hours");

        try (Connection conn = DBConnection.getConnection()) {
            AbsenceDAO absenceDao = new AbsenceDAO(conn);

            Absence absence = new Absence();
            absence.setEmployeeId(employeeId);
            absence.setDate(Date.valueOf(java.time.LocalDate.parse(dateStr)));
            absence.setType(type);
            absence.setHours(Integer.parseInt(hoursStr));

            absenceDao.save(absence);

            // ✅ Redirection vers doGet du même servlet
            resp.sendRedirect("AbsenceServlet?success=absence");
            return;

        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de la déclaration d'absence", e);
        }
    }
}
