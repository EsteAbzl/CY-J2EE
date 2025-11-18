<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des projets</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg,#1e3c72,#2a5298); min-height:100vh; padding:2rem; font-family:Arial; display:flex; justify-content:center; align-items:flex-start; }
        .card { background:#fff; border-radius:12px; box-shadow:0 8px 20px rgba(0,0,0,0.25); padding:2rem; width:100%; max-width:900px; }
        .card h2 { text-align:center; margin-bottom:1.5rem; color:#2a5298; }
        .table thead { background-color:#2a5298; color:#fff; }
        .btn-primary { background-color:#2a5298; border:none; border-radius:8px; font-weight:bold; }
        .btn-primary:hover { background-color:#1e3c72; }
        .footer { text-align:center; margin-top:1rem; }
        .footer a { color:#2a5298; text-decoration:none; }
        .footer a:hover { text-decoration:underline; }
    </style>
</head>
<body>
<div class="card">
    <h2>Liste des projets</h2>
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Nom</th>
            <th>Responsable</th>
            <th>Date début</th>
            <th>Date fin</th>
        </tr>
        </thead>
        <tbody>
        <!-- Données statiques d’exemple -->
        <tr>
            <td>Migration Système</td>
            <td>Paul Durand</td>
            <td>2025-01-10</td>
            <td>2025-06-30</td>
        </tr>
        <tr>
            <td>Application Mobile</td>
            <td>Sophie Leroy</td>
            <td>2025-03-01</td>
            <td>2025-09-15</td>
        </tr>
        <tr>
            <td>Campagne Marketing</td>
            <td>Jean Martin</td>
            <td>2025-02-20</td>
            <td>2025-05-30</td>
        </tr>
        </tbody>
    </table>
    <div class="footer">
        <a href="dashboardRH.jsp" class="btn btn-primary">Retour au tableau de bord RH</a>
    </div>
</div>
</body>
</html>
