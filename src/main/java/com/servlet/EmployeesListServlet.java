package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Employe;
import com.model.Departement;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/EmployeesListServlet")
public class EmployeesListServlet extends HttpServlet {
    // ⚡ À adapter selon ta configuration MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cy_j2ee_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Employe> employes = new ArrayList<>();
        List<Departement> departements = new ArrayList<>();
        List<String> grades = new ArrayList<>();
        List<String> postes = new ArrayList<>();

        // Récupérer les paramètres de recherche et filtrage
        String searchParam = request.getParameter("search");
        String departementFilter = request.getParameter("departement");
        String gradeFilter = request.getParameter("grade");
        String posteFilter = request.getParameter("poste");

        try {
            // Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // Construire la requête SQL avec les filtres
            StringBuilder sqlBuilder = new StringBuilder("SELECT id, nom, prenom, email, poste, grade, idDepartement FROM Employe WHERE 1=1");

            // Filtrer par recherche (nom, prénom ou id)
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                sqlBuilder.append(" AND (nom LIKE ? OR prenom LIKE ? OR id LIKE ?)");
            }

            // Filtrer par département
            if (departementFilter != null && !departementFilter.isEmpty() && !departementFilter.equals("0")) {
                sqlBuilder.append(" AND idDepartement = ?");
            }

            // Filtrer par grade
            if (gradeFilter != null && !gradeFilter.isEmpty()) {
                sqlBuilder.append(" AND grade = ?");
            }

            // Filtrer par poste
            if (posteFilter != null && !posteFilter.isEmpty()) {
                sqlBuilder.append(" AND poste = ?");
            }

            String sql = sqlBuilder.toString();
            PreparedStatement stmt = conn.prepareStatement(sql);

            // Bind les paramètres de recherche
            int paramIndex = 1;
            if (searchParam != null && !searchParam.trim().isEmpty()) {
                String searchValue = "%" + searchParam + "%";
                stmt.setString(paramIndex++, searchValue);
                stmt.setString(paramIndex++, searchValue);
                stmt.setString(paramIndex++, searchValue);
            }

            if (departementFilter != null && !departementFilter.isEmpty() && !departementFilter.equals("0")) {
                stmt.setInt(paramIndex++, Integer.parseInt(departementFilter));
            }

            if (gradeFilter != null && !gradeFilter.isEmpty()) {
                stmt.setString(paramIndex++, gradeFilter);
            }

            if (posteFilter != null && !posteFilter.isEmpty()) {
                stmt.setString(paramIndex++, posteFilter);
            }

            // Exécuter la requête
            ResultSet rs = stmt.executeQuery();

            // Parcours des résultats
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prenom");
                String email = rs.getString("email");
                String poste = rs.getString("poste");
                String grade = rs.getString("grade");
                int idDepartement = rs.getInt("idDepartement");
                employes.add(new Employe(id, nom, prenom, email, poste, grade, idDepartement));
            }

            rs.close();
            stmt.close();

            // Récupérer tous les départements pour le filtre
            String deptSql = "SELECT DISTINCT id, nom FROM Departement ORDER BY nom";
            PreparedStatement deptStmt = conn.prepareStatement(deptSql);
            ResultSet deptRs = deptStmt.executeQuery();
            while (deptRs.next()) {
                departements.add(new Departement(deptRs.getInt("id"), deptRs.getString("nom")));
            }
            deptRs.close();
            deptStmt.close();

            // Récupérer tous les grades uniques
            String gradeSql = "SELECT DISTINCT grade FROM Employe ORDER BY grade";
            PreparedStatement gradeStmt = conn.prepareStatement(gradeSql);
            ResultSet gradeRs = gradeStmt.executeQuery();
            while (gradeRs.next()) {
                grades.add(gradeRs.getString("grade"));
            }
            gradeRs.close();
            gradeStmt.close();

            // Récupérer tous les postes uniques
            String posteSql = "SELECT DISTINCT poste FROM Employe ORDER BY poste";
            PreparedStatement posteStmt = conn.prepareStatement(posteSql);
            ResultSet posteRs = posteStmt.executeQuery();
            while (posteRs.next()) {
                postes.add(posteRs.getString("poste"));
            }
            posteRs.close();
            posteStmt.close();

            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mettre les listes en attribut
        request.setAttribute("employes", employes);
        request.setAttribute("departements", departements);
        request.setAttribute("grades", grades);
        request.setAttribute("postes", postes);
        request.setAttribute("searchParam", searchParam);
        request.setAttribute("departementFilter", departementFilter);
        request.setAttribute("gradeFilter", gradeFilter);
        request.setAttribute("posteFilter", posteFilter);

        // Forward vers la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("employeesList.jsp");
        dispatcher.forward(request, response);
    }
}
