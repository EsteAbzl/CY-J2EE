<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Liste des employés</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>body{background:linear-gradient(135deg,#1e3c72,#2a5298);min-height:100vh;padding:2rem;font-family:Arial;}
    .card{background:#fff;border-radius:12px;box-shadow:0 8px 20px rgba(0,0,0,0.25);padding:2rem;}
    .card h2{text-align:center;margin-bottom:1.5rem;color:#2a5298;}
    .table thead{background-color:#2a5298;color:#fff;}</style></head>
<body>
<div class="card"><h2>Liste des employés</h2>
    <table class="table table-striped">
        <thead><tr><th>Nom</th><th>Prénom</th><th>Email</th><th>Poste</th><th>Département</th></tr></thead>
        <tbody>
        <tr><td>Dupont</td><td>Marie</td><td>marie@entreprise.com</td><td>RH</td><td>Ressources Humaines</td></tr>
        <tr><td>Martin</td><td>Jean</td><td>jean@entreprise.com</td><td>Développeur</td><td>Informatique</td></tr>
        </tbody></table></div>
</body></html>
