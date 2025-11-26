<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Employee" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        h2 { text-align:center; color:#fff; margin-bottom:1rem; }
        table { width:100%; border-collapse:collapse; margin:auto; background:#fff; border-radius:12px; overflow:hidden; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        th, td { padding:.75rem; border:1px solid #ddd; text-align:left; }
        th { background:#2a5298; color:#fff; }
        tr:nth-child(even) { background:#f9f9f9; }
        .btn { display:inline-block; background:#2a5298; color:#fff; text-decoration:none; padding:.5rem 1rem; border-radius:8px; font-weight:bold; margin:.25rem; transition:.3s; }
        .btn:hover { background:#1e3c72; }
        .btn-danger { background:#c0392b; }
        .btn-danger:hover { background:#962d22; }
        .actions { text-align:center; margin-top:1rem; }
    </style>
</head>
<body>
<h2>Liste des employés</h2>
<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Email</th>
        <th>Grade</th>
        <th>Poste</th>
        <th>ID Département</th>
        <th>Actions</th>
    </tr>
    <%
        List<Employee> employees = (List<Employee>) request.getAttribute("employees");
        if (employees != null) {
            for (Employee e : employees) {
    %>
    <tr>
        <td><%= e.getId() %></td>
        <td><%= e.getLastName() %> <%= e.getFirstName() %></td>
        <td><%= e.getEmail() %></td>
        <td><%= e.getGrade() %></td>
        <td><%= e.getPositionTitle() %></td>
        <td><%= e.getDepartmentId()%></td>
        <td>
            <a class="btn" href="EmployeeEditServlet?id=<%= e.getId() %>">✏️ Modifier</a>
            <a class="btn btn-danger" href="EmployeeDeleteServlet?id=<%= e.getId() %>" onclick="return confirm('Supprimer cet employé ?');">🗑️ Supprimer</a>
        </td>
    </tr>
    <% } } %>
</table>

<div class="actions">
    <a class="btn" href="addEmployee.jsp">➕ Ajouter un employé</a>
    <a class="btn" href="dashboard.jsp">🏠 Retour au tableau de bord</a>
</div>
</body>
</html>
