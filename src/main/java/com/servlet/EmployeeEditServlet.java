package com.servlet;

import com.dao.DepartmentDAO;
import com.dao.EmployeeDAO;
import com.dao.SalaireDAO;
import com.model.Department;
import com.model.Employee;
import com.model.Salaire;
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
import java.sql.Date;
import java.util.List;

@WebServlet("/EmployeeEditServlet")
public class EmployeeEditServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PermissionUtil.manageConnexionPermission(req, resp, PermissionUtil.isConnexionAllowed(req, new Integer[] {1}));

        int id = Integer.parseInt(req.getParameter("id"));
        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            Employee emp = dao.findById(id);
            req.setAttribute("employee", emp);

            java.util.Date dateArriveEmployee = dao.arrivingDate(Integer.valueOf(id));
            req.setAttribute("arriving_date", (dateArriveEmployee.getYear()+1900) + "-" + String.format("%02d", (dateArriveEmployee.getMonth()+1)) + "-" + String.format("%02d", (dateArriveEmployee.getDay()+1)));

            DepartmentDAO departmentDao = new DepartmentDAO(conn);
            List<Department> departments = departmentDao.findAll();
            req.setAttribute("departments", departments);

            req.getRequestDispatcher("editEmployee.jsp").forward(req, resp);

        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de la récupération de l'employé", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String firstName = req.getParameter("first_name");
        String lastName = req.getParameter("last_name");
        String email = req.getParameter("email");
        String grade = req.getParameter("grade");
        String positionTitle = req.getParameter("position_title");
        double baseSalary = Double.parseDouble(req.getParameter("salary"));
        Date editDate = java.sql.Date.valueOf(req.getParameter("edit_date"));
        int departmentId = Integer.parseInt(req.getParameter("department_id"));
        boolean active = req.getParameter("active") != null;

        Employee emp = new Employee();
        emp.setId(id);
        emp.setFirstName(firstName);
        emp.setLastName(lastName);
        emp.setEmail(email);
        emp.setGrade(grade);
        emp.setPositionTitle(positionTitle);
        emp.setBaseSalary(baseSalary);
        emp.setDepartmentId(departmentId);
        emp.setActive(active);

        try (Connection conn = DBConnection.getConnection()) {
            EmployeeDAO dao = new EmployeeDAO(conn);
            dao.update(emp);

            SalaireDAO salaireDao = new SalaireDAO(conn);
            Salaire salaire = new Salaire();
            salaire.setSalaire(emp.getBaseSalary());
            salaire.setDate(editDate);
            salaire.setEmployeeId(emp.getId());
            salaireDao.create(salaire);

            resp.sendRedirect("dashboard.jsp?success=update");
        } catch (SQLException e) {
            throw new ServletException("Erreur SQL lors de la mise à jour de l'employé", e);
        }
    }
}


