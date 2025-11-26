<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model.Project" %>
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
        form { background:#fff; border-radius:12px; padding:2rem; max-width:500px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h2>Modifier un projet</h2>
<form action="ProjectEditServlet" method="post">
    <input type="hidden" name="id" value="<%= proj.getId() %>">
    <label>Nom</label><input type="text" name="name" value="<%= proj.getName() %>" required>
    <label>Description</label><input type="text" name="description" value="<%= proj.getDescription() %>">
    <label>Status</label><input type="text" name="status" value="<%= proj.getStatus() %>">
    <label>Date de début</label><input type="date" name="start_date" value="<%= proj.getStartDate() %>">
    <label>Date de fin</label><input type="date" name="end_date" value="<%= proj.getEndDate() %>">
    <label>ID Département</label><input type="number" name="department_id" value="<%= proj.getDepartmentId() %>">
    <button class="btn" type="submit">Mettre à jour</button>
</form>
</body>
</html>
