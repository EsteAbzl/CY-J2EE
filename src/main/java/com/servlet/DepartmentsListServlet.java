package com.servlet;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.model.Departement;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/DepartmentsListServlet")
public class DepartmentsListServlet extends HttpServlet {
    // ⚡ À adapter selon ta configuration MySQL
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cy_j2ee_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        List<Departement> departements = new ArrayList<>();

        try {
            // Charger le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connexion
            Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

            // Requête SQL
            String sql = "SELECT id, nom FROM Departement";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Parcours des résultats
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                departements.add(new Departement(id, nom));
            }

            rs.close();
            stmt.close();
            conn.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mettre la liste en attribut
        request.setAttribute("departements", departements);

        // Forward vers la JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("departmentsList.jsp");
        dispatcher.forward(request, response);

        System.out.println("Nombre de départements : " + departements.size());
        for (Departement d : departements) {
            System.out.println(d.getId() + " - " + d.getNom());
        }

    }
}
