package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Projet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/ProjectsListServlet")
public class ProjectsListServlet extends HttpServlet {
    // ⚡ À adapter selon ta configuration MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cy_j2ee_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Projet> projets = new ArrayList<>();

        try {
            // Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // Requête SQL
            String sql = "SELECT id, nom, avancement FROM Projet";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Parcours des résultats
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String avancement = rs.getString("avancement");
                projets.add(new Projet(id, nom, avancement));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mettre la liste en attribut
        request.setAttribute("projets", projets);

        // Forward vers la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("projectsList.jsp");
        dispatcher.forward(request, response);
    }
}
