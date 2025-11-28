<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Project, com.model.Employee" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Détails du projet</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        h1, h2 { color:#fff; }
        .container { max-width:1000px; margin:0 auto; background:#fff; border-radius:12px; box-shadow:0 8px 20px rgba(0,0,0,0.25); padding:2rem; }
        .project-info { margin-bottom:2rem; }
        .project-info label { font-weight:bold; color:#2a5298; }
        .project-info p { margin:0.5rem 0; color:#555; }
        .members { margin-top:2rem; }
        table { width:100%; border-collapse:collapse; margin-top:1rem; }
        th, td { padding:0.75rem; border:1px solid #ddd; text-align:left; }
        th { background:#2a5298; color:#fff; }
        tr:nth-child(even) { background:#f9f9f9; }
        .btn { display:inline-block; background:#2a5298; color:#fff; text-decoration:none; padding:0.5rem 1rem; border-radius:8px; font-weight:bold; margin:0.5rem; transition:0.3s; }
        .btn:hover { background:#1e3c72; }
        .btn-danger { background:#c0392b; }
        .btn-danger:hover { background:#962d22; }
        .actions { text-align:center; margin-top:2rem; }
    </style>
</head>
<body>
<div class="container">
    <h1>Détails du projet</h1>
    
    <div class="project-info">
        <label>Nom :</label>
        <p>${project.name}</p>

        <label>Description :</label>
        <p>${project.description != null ? project.description : 'N/A'}</p>

        <label>Département :</label>
        <p>${departmentName}</p>

        <label>Statut :</label>
        <p>${project.status}</p>

        <label>Date de début :</label>
        <p>${project.startDate != null ? project.startDate : 'N/A'}</p>

        <label>Date de fin :</label>
        <p>${project.endDate != null ? project.endDate : 'N/A'}</p>
    </div>

    <div class="members">
        <h2>Membres du projet</h2>
        <% 
            List<Employee> members = (List<Employee>) request.getAttribute("members");
            if (members != null && !members.isEmpty()) {
        %>
        <table>
            <tr>
                <th>Nom</th>
                <th>Email</th>
                <th>Rôle dans le projet</th>
            </tr>
            <% for (Employee m : members) { %>
            <tr>
                <td><%= m.getLastName() %> <%= m.getFirstName() %></td>
                <td><%= m.getEmail() %></td>
                <td><%= m.getRoleInProject() %></td>
            </tr>
            <% } %>
        </table>
        <% } else { %>
        <p>Aucun membre assigné à ce projet.</p>
        <% } %>
    </div>

    <div class="actions">
        <% 
            Boolean canManage = (Boolean) request.getAttribute("canManage");
            if (canManage != null && canManage) {
        %>
        <a href="ProjectEditServlet?id=${project.id}" class="btn">✏️ Modifier</a>
        <a href="ProjectAssignmentServlet?project_id=${project.id}" class="btn">➕ Affecter employé</a>
        <a href="ProjectDeleteServlet?id=${project.id}" class="btn btn-danger" onclick="return confirm('Supprimer ce projet ?');">🗑️ Supprimer</a>
        <% } %>
        <a href="ProjectsListServlet" class="btn">← Retour aux projets</a>
    </div>
</div>
</body>
</html>
