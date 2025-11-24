<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Project" %>
<%
    List<Project> projects = (List<Project>) request.getAttribute("projects");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Manager</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h1 { text-align:center; color:#fff; }
        .card { background:#fff; border-radius:12px; padding:1.5rem; margin:1rem auto; max-width:800px; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        table { width:100%; border-collapse:collapse; margin-top:1rem; }
        th, td { border:1px solid #ccc; padding:.5rem; text-align:left; }
        th { background:#eee; }
        .btn { display:inline-block; background:#2a5298; color:#fff; padding:.5rem 1rem; border-radius:8px; text-decoration:none; margin-top:1rem; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h1>Dashboard Manager</h1>

<div class="card">
    <h2>Mes projets</h2>
    <table>
        <tr><th>Nom</th><th>Statut</th><th>Date début</th><th>Date fin</th></tr>
        <% if (projects != null) {
            for (Project p : projects) { %>
        <tr>
            <td><%= p.getName() %></td>
            <td><%= p.getStatus() %></td>
            <td><%= p.getStartDate() %></td>
            <td><%= p.getEndDate() %></td>
        </tr>
        <% } } %>
    </table>
    <a class="btn" href="ProjectCreateServlet.jsp">➕ Nouveau projet</a>
</div>

<div class="card">
    <h2>Mon équipe</h2>
    <p>Accédez à la liste des employés de votre département.</p>
    <a class="btn" href="employeesList.jsp">👥 Voir mon équipe</a>
</div>
</body>
</html>
