<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Membres du projet</title>
    <style>
        body { font-family: Arial, sans-serif; background:linear-gradient(135deg,#1e3c72,#2a5298); margin:0; padding:0; min-height:100vh; }
        header { background:#2c3e50; color:#fff; padding:15px; text-align:center; }
        h2 { text-align:center; color:#fff; margin:20px 0; }
        table { border-collapse:collapse; width:90%; margin:20px auto; background:#fff; box-shadow:0 8px 20px rgba(0,0,0,0.25); border-radius:8px; overflow:hidden; }
        th, td { border:1px solid #ddd; padding:10px 15px; text-align:left; }
        th { background:#34495e; color:#fff; }
        tr:nth-child(even) { background:#f2f2f2; }
        .actions { text-align:center; margin:20px; }
        .link { text-decoration:none; background:#27ae60; color:#fff; padding:8px 16px; border-radius:4px; font-weight:bold; }
        .remove-btn { background:#e74c3c; color:#fff; border:none; padding:6px 12px; border-radius:4px; cursor:pointer; font-weight:bold; }
        .remove-btn:hover { background:#c0392b; }
        .success-msg { background:#27ae60; color:#fff; padding:10px; text-align:center; margin:10px auto; width:90%; border-radius:4px; }
    </style>
</head>
<body>
<header>
    <h1>Membres du projet</h1>
</header>

<h2>Liste des membres</h2>

<c:if test="${param.success == 'removed'}">
    <div class="success-msg">L'employé a été retiré du projet avec succès.</div>
</c:if>

<c:if test="${empty members}">
    <p style="text-align:center; color:#fff;">Aucun membre trouvé pour ce projet.</p>
</c:if>

<c:if test="${not empty members}">
    <table>
        <thead>
        <tr>
            <th>ID Projet</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Role</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="m" items="${members}">
            <tr>
                <td>${m.id}</td>
                <td>${m.firstName} ${m.lastName}</td>
                <td>${m.email}</td>
                <td>${m.roleInProject}</td>
                <td>
                    <form action="ProjectMembersServlet" method="post" style="display:inline;" onsubmit="return confirm('Êtes-vous sûr de vouloir retirer cet employé du projet ?');">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="project_id" value="${project.id}">
                        <input type="hidden" name="employee_id" value="${m.id}">
                        <button type="submit" class="remove-btn">Retirer</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<div class="actions">
    <a class="link" href="ProjectsListServlet">Retour à la liste des projets</a>
</div>
</body>
</html>
