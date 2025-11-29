<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Tableau de bord RH</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg,#1e3c72,#2a5298); min-height:100vh; font-family:Arial; padding:2rem; }
        .dashboard { display:grid; grid-template-columns:repeat(auto-fit,minmax(280px,1fr)); gap:2rem; }
        .card { background:#fff; border-radius:12px; box-shadow:0 8px 20px rgba(0,0,0,0.25); padding:2rem; text-align:center; }
        .card h2 { margin-bottom:1rem; color:#2a5298; }
        .btn-primary { background-color:#2a5298; border:none; border-radius:8px; font-weight:bold; margin:.25rem 0; }
        .btn-primary:hover { background-color:#1e3c72; }
    </style>
</head>
<body>
<h1 class="text-center text-white mb-4">Tableau de bord RH</h1>
<div class="dashboard">
    <div class="card">
        <h2>Employés</h2>
        <a href="addEmployeeServlet" class="btn btn-primary w-100">Ajouter</a>
        <a href="deleteEmployee.jsp" class="btn btn-primary w-100">Supprimer</a>
        <a href="EmployeesListServlet" class="btn btn-primary w-100">Voir la liste</a>
        <a href="generatePayslip.jsp" class="btn btn-primary w-100">Générer une fiche de paie</a>
    </div>
    <div class="card">
        <h2>Départements</h2>
        <a href="addDepartment.jsp" class="btn btn-primary w-100">Ajouter</a>
        <a href="deleteDepartment.jsp" class="btn btn-primary w-100">Supprimer</a>
        <a href="DepartmentsListServlet" class="btn btn-primary w-100">Voir la liste</a>
        <a href="employeesByDepartment.jsp" class="btn btn-primary w-100">Employés par département</a>
    </div>
    <div class="card">
        <h2>Projets</h2>
        <a href="ProjectCreateServlet" class="btn btn-primary w-100">Ajouter</a>
        <a href="deleteProject.jsp" class="btn btn-primary w-100">Supprimer</a>
        <a href="ProjectsListServlet" class="btn btn-primary w-100">Voir la liste</a>
        <a href="employeesByProject.jsp" class="btn btn-primary w-100">Employés par projet</a>
    </div>
</div>
</body>
</html>