package com.servlet;

import java.io.IOException;
import java.sql.*;
import com.model.Projet;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/EditProjectServlet")
public class EditProjectServlet extends HttpServlet {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/cy_j2ee_db?useSSL=false&serverTimezone=UTC";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASS = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

                String sql = "SELECT id, nom, avancement FROM Projet WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                Projet proj = null;
                if (rs.next()) {
                    proj = new Projet(rs.getInt("id"), rs.getString("nom"), rs.getString("avancement"));
                }

                rs.close();
                stmt.close();
                conn.close();

                request.setAttribute("projet", proj);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editProject.jsp");
                dispatcher.forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String nom = request.getParameter("nom");
        String avancement = request.getParameter("avancement");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

                String sql = "UPDATE Projet SET nom=?, avancement=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nom);
                stmt.setString(2, avancement);
                stmt.setInt(3, id);
                stmt.executeUpdate();

                stmt.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("ProjectsListServlet");
    }
}

