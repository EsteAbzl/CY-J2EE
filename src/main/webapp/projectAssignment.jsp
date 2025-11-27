<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Affecter un employé à un projet</title>
    <style>
        body { font-family: Arial, sans-serif; background:linear-gradient(135deg,#1e3c72,#2a5298); margin:0; padding:0; min-height:100vh; }
        header { background:#2c3e50; color:#fff; padding:15px; text-align:center; }
        h2 { text-align:center; color:#fff; margin:20px 0; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:500px; margin:30px auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1.5rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
        .back { text-align:center; margin:20px; }
        .back a { text-decoration:none; background:#27ae60; color:#fff; padding:8px 16px; border-radius:4px; font-weight:bold; }
    </style>
</head>
<body>
<header>
    <h1>Affecter un employé à un projet</h1>
</header>

<h2>Nouvelle affectation</h2>
<form action="ProjectAssignmentServlet" method="post">
    <label>ID Projet</label>
    <input type="number" name="project_id" value="${param.project_id}">

    <label>ID Employé</label>
    <input type="number" name="employee_id" required>

    <label>Rôle dans le projet</label>
    <input type="text" name="role_in_project" required>

    <button class="btn" type="submit">Affecter</button>
</form>

<div class="back">
    <a href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
