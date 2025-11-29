<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); min-height:100vh; font-family:Arial; margin:0; padding:2rem; }
        h1 { text-align:center; color:#fff; margin-bottom:2rem; }
        .dashboard { display:grid; grid-template-columns:repeat(auto-fit,minmax(280px,1fr)); gap:2rem; }
        .card { background:#fff; border-radius:12px; box-shadow:0 8px 20px rgba(0,0,0,0.25); padding:2rem; text-align:center; }
        .card h2 { margin-bottom:1rem; color:#2a5298; }
        .btn { display:block; background:#2a5298; color:#fff; text-decoration:none; padding:.75rem; border-radius:8px; font-weight:bold; margin:.25rem 0; transition:.3s; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<div class="logout">
    <jsp:include page="LogoutButton.jsp" />
</div>
<h1>Tableau de bord</h1>
<div class="dashboard">
    <div class="card">
        <h2>Employés</h2>
        <a href="addEmployeeServlet" class="btn">Ajouter</a>
        <a href="EmployeeDeleteServlet" class="btn">Supprimer</a>
        <a href="EmployeeListServlet" class="btn">Voir la liste</a>
        <a href="PayslipListServlet" class="btn">Fiches de paie</a>
        <a href="AbsenceListServlet" class="btn">Liste d'absences</a>
    </div>
    <div class="card">
        <h2>Départements</h2>
        <a href="DepartmentCreateServlet" class="btn">Ajouter</a>
        <a href="deleteDepartment.jsp" class="btn">Supprimer</a>
        <a href="DepartmentsListServlet" class="btn">Voir la liste</a>
    </div>
    <div class="card">
        <h2>Projets</h2>
        <a href="addProject.jsp" class="btn">Ajouter</a>
        <a href="deleteProject.jsp" class="btn">Supprimer</a>
        <a href="ProjectsListServlet" class="btn">Voir la liste</a>
        <a href="projectAssignment.jsp" class="btn">Affecter employés</a>
    </div>
    <div class="card">
        <h2>Rapports</h2>
        <a href="ReportsServlet" class="btn">Statistiques</a>
    </div>
</div>
</body>
</html>
