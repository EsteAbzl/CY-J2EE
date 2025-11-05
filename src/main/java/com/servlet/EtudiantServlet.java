package com.servlet;

import com.dao.EtudiantDAO;
import com.model.Etudiant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.util.List;

@WebServlet("/etudiant")
public class EtudiantServlet extends HttpServlet {

    private final EtudiantDAO dao = new EtudiantDAO();

    @Override
    protected void doGet(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        // Affiche liste des étudiants
        List<Etudiant> list = dao.findAll();
        req.setAttribute("etudiants", list);
        req.getRequestDispatcher("/WEB-INF/ListeEtudiant.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, jakarta.servlet.http.HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("add".equals(action)) {
            String nom = req.getParameter("nom");
            String prenom = req.getParameter("prenom");
            String ageStr = req.getParameter("age");
            Integer age = (ageStr == null || ageStr.isEmpty()) ? null : Integer.parseInt(ageStr);

            Etudiant e = new Etudiant(nom, prenom, age);
            dao.save(e);
            resp.sendRedirect(req.getContextPath() + "/etudiant");
        } else if ("delete".equals(action)) {
            String idStr = req.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                dao.delete(Integer.parseInt(idStr));
            }
            resp.sendRedirect(req.getContextPath() + "/etudiant");
        } else {
            resp.sendError(400, "Action inconnue");
        }
    }
}
