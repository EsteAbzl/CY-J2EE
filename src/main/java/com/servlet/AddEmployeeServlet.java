package com.servlet;

import java.io.IOException;
import java.text.Normalizer;

import com.dao.DepartementDAO;
import com.dao.EmployeDAO;
import com.model.Departement;
import com.model.Employe;
import com.util.PasswordUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/addEmployee")
public class AddEmployeeServlet extends HttpServlet {

    private final EmployeDAO employeDAO = new EmployeDAO();
    private final DepartementDAO departementDAO = new DepartementDAO();

    private String slugify(String s){
        if(s == null) return "";
        String normalized = Normalizer.normalize(s, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        return normalized.trim().toLowerCase().replaceAll("[^a-z0-9]+", ".")
                .replaceAll("^\\.|\\.$", "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        String nom = req.getParameter("nom");
        String prenom = req.getParameter("prenom");
        String poste = req.getParameter("poste");
        String departementName = req.getParameter("departement");
    // dateEmbauche is available from the form as req.getParameter("dateEmbauche") if needed

        String local = slugify(nom) + (prenom != null && !prenom.isEmpty() ? "." + slugify(prenom) : "");
        String domain = "@entreprise.com";
        String email = local.isEmpty() ? "" : local + domain;

        // ensure uniqueness: append a number if needed
        if(!email.isEmpty()){
            String candidate = email;
            int i = 2;
            while(employeDAO.findByEmail(candidate) != null){
                candidate = local + i + domain;
                i++;
            }
            email = candidate;
        }

        Employe e = new Employe();
        e.setNom(nom);
        e.setPrenom(prenom);
        e.setPoste(poste);
        e.setEmail(email);
        // default password 'test' hashed
    e.setMdp(PasswordUtil.hashPassword("test"));
    e.setPremiereConnexion(true);
    // ensure DB NOT NULL columns have safe defaults
    e.setGrade("EMPLOYE");
    e.setEstArchive(false);

        // map departement name to id if possible; fallback to 'Administration'
        Integer departementId = null;
        if(departementName != null && !departementName.isEmpty()){
            Departement d = departementDAO.findByName(departementName);
            if(d != null) departementId = d.getId();
        }
        if(departementId == null){
            Departement admin = departementDAO.findByName("Administration");
            if(admin != null) departementId = admin.getId();
        }
        // final fallback: 1 (assumes administration or first dept exists)
        if(departementId == null) departementId = 1;
        e.setIdDepartement(departementId);

        // ensure email is not empty (DB requires NOT NULL). If slug fails, create a safe unique fallback.
        if(email == null || email.isEmpty()){
            email = "user" + System.currentTimeMillis() + "@entreprise.com";
            // ensure uniqueness though improbable with timestamp
            int j = 2;
            while(employeDAO.findByEmail(email) != null){
                email = "user" + System.currentTimeMillis() + j + "@entreprise.com";
                j++;
            }
            e.setEmail(email);
        }

        employeDAO.save(e);

        // redirect back to dashboard or list
        resp.sendRedirect(req.getContextPath() + "/dashboardRH.jsp");
    }
}
