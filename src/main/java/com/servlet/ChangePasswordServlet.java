package com.servlet;

import java.io.IOException;

import com.dao.EmployeDAO;
import com.model.Employe;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/changePassword")
public class ChangePasswordServlet extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Object obj = req.getSession().getAttribute("employe");
        if (!(obj instanceof Employe)) {
            resp.sendRedirect(req.getContextPath() + "/Login.jsp");
            return;
        }
        Employe e = (Employe) obj;

        String newMdp = req.getParameter("newMdp");
        String confirmMdp = req.getParameter("confirmMdp");

        if (newMdp == null || confirmMdp == null || !newMdp.equals(confirmMdp)) {
            resp.sendRedirect(req.getContextPath() + "/changePassword.jsp?error=nomatch");
            return;
        }

    // Store hashed password
    e.setMdp(com.util.PasswordUtil.hashPassword(newMdp));
        e.setPremiereConnexion(false);
        employeDAO.save(e);

        // Update session with latest values
        req.getSession().setAttribute("employe", e);

        resp.sendRedirect(req.getContextPath() + "/dashboardRH.jsp");
    }
}
