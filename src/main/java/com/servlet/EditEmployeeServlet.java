package com.servlet;

import com.model.Employe;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/EditEmployeeServlet")
public class EditEmployeeServlet extends HttpServlet {
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

                String sql = "SELECT * FROM Employe WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                Employe emp = null;
                if (rs.next()) {
                    emp = new Employe();
                    emp.setNom(rs.getString("nom"));
                    emp.setPrenom(rs.getString("prenom"));
                    emp.setEmail(rs.getString("email"));
                    emp.setPoste(rs.getString("poste"));
                    emp.setGrade(rs.getString("grade"));
                    emp.setIdDepartement(rs.getInt("idDepartement"));
                }

                rs.close();
                stmt.close();
                conn.close();

                request.setAttribute("employe", emp);
                RequestDispatcher dispatcher = request.getRequestDispatcher("editEmployee.jsp");
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
        String prenom = request.getParameter("prenom");
        String email = request.getParameter("email");
        String poste = request.getParameter("poste");
        String grade = request.getParameter("grade");

        if (idParam != null) {
            try {
                int id = Integer.parseInt(idParam);

                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASS);

                String sql = "UPDATE Employe SET nom=?, prenom=?, email=?, poste=?, grade=? WHERE id=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nom);
                stmt.setString(2, prenom);
                stmt.setString(3, email);
                stmt.setString(4, poste);
                stmt.setString(5, grade);
                stmt.setInt(6, id);
                stmt.executeUpdate();

                stmt.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        response.sendRedirect("EmployeesListServlet");
    }
}
