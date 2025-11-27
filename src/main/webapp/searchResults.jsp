<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Employee" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Résultats de recherche</title>
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
        .search-bar {
            display: flex;
            justify-content: center;
            margin: 20px;
        }
        .search-bar input {
            padding: 0.5rem;
            border: 1px solid #ccc;
            border-radius: 8px 0 0 8px;
            width: 250px;
        }
        .search-bar button {
            padding: 0.5rem 1rem;
            border: none;
            background: #2a5298;
            color: white;
            border-radius: 0 8px 8px 0;
            cursor: pointer;
        }
        .search-bar button:hover {
            background: #1e3c72;
        }
    </style>
</head>
<body>
<h2>Résultats de recherche pour "<%= request.getAttribute("searchQuery") %>"</h2>

<div>
    <form action="SearchServlet" method="get" class="search-bar">
        <input type="text" name="query" placeholder="Nom, prénom ou matricule">
        <button type="submit">🔍 Rechercher</button>
    </form>
</div>

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
        List<Employee> results = (List<Employee>) request.getAttribute("results");
        if (results != null && !results.isEmpty()) {
            for (Employee e : results) {
    %>
    <tr>
        <td><%= e.getId() %></td>
        <td><%= e.getLastName() %> <%= e.getFirstName() %></td>
        <td><%= e.getEmail() %></td>
        <td><%= e.getGrade() %></td>
        <td><%= e.getPositionTitle() %></td>
        <td><%= e.getDepartmentId() %></td>
        <td>
            <a class="btn" href="EmployeeEditServlet?id=<%= e.getId() %>">✏️ Modifier</a>
            <a class="btn btn-danger" href="EmployeeDeleteServlet?id=<%= e.getId() %>" onclick="return confirm('Supprimer cet employé ?');">🗑️ Supprimer</a>
        </td>
    </tr>
    <% } } else { %>
    <tr>
        <td colspan="7" style="text-align:center;">Aucun employé trouvé.</td>
    </tr>
    <% } %>
</table>

<div class="actions">
    <a class="btn" href="addEmployee">➕ Ajouter un employé</a>
    <a class="btn" href="dashboard.jsp">🏠 Retour au tableau de bord</a>
</div>
</body>
</html>
