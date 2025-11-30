<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model.Project, java.util.List, com.model.Department" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    Project proj = (Project) request.getAttribute("project");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un projet</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h2 { text-align:center; color:#fff; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:600px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input, textarea, select { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; box-sizing:border-box; }
        textarea { resize:vertical; min-height:80px; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h2>Modifier un projet</h2>
<form action="ProjectEditServlet" method="post">
    <input type="hidden" name="id" value="<%= proj.getId() %>">
    
    <label>Nom</label>
    <input type="text" name="name" value="<%= proj.getName() %>" required>
    
    <label>Description</label>
    <textarea name="description"><%= proj.getDescription() != null ? proj.getDescription() : "" %></textarea>
    
    <label>Status</label>
    <select name="status" required>
        <option value="EN_COURS" <% if ("EN_COURS".equals(proj.getStatus())) { %>selected<% } %>>En cours</option>
        <option value="TERMINE" <% if ("TERMINE".equals(proj.getStatus())) { %>selected<% } %>>Terminé</option>
        <option value="ANNULE" <% if ("ANNULE".equals(proj.getStatus())) { %>selected<% } %>>Annulé</option>
    </select>
    
    <label>Date de début</label>
    <input type="date" name="start_date" value="<%= proj.getStartDate() %>" required>
    
    <label>Date de fin</label>
    <input type="date" name="end_date" value="<%= proj.getEndDate() %>" required>
    
    <label>Département</label>
    <select name="department_id" required>
        <option value="">-- Choisir un département --</option>
        <c:forEach var="d" items="${departments}">
            <option value="${d.id}" <c:if test="${d.id == proj.departmentId}">selected</c:if>>${d.name}</option>
        </c:forEach>
    </select>

    <div style="display:flex; gap:1rem; margin-top:1.5rem;">
        <a class="btn" href="ProjectsListServlet" style="background:#888; text-align:center; text-decoration:none; flex:1;">Retour</a>
        <button class="btn" type="submit" style="flex:1;">Mettre à jour</button>
    </div>

</form>
</body>
</html>
