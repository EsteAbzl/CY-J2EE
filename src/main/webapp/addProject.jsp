<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un projet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background: linear-gradient(135deg,#1e3c72,#2a5298); min-height:100vh; display:flex; justify-content:center; align-items:center; font-family:Arial; }
        .card { background:#fff; border-radius:12px; box-shadow:0 8px 20px rgba(0,0,0,0.25); padding:2rem; width:100%; max-width:500px; }
        .card h2 { text-align:center; margin-bottom:1.5rem; color:#2a5298; }
        .form-control { border-radius:8px; }
        .btn-primary { background-color:#2a5298; border:none; border-radius:8px; font-weight:bold; }
        .btn-primary:hover { background-color:#1e3c72; }
        .footer { text-align:center; margin-top:1rem; }
        .footer a { color:#2a5298; text-decoration:none; }
        .footer a:hover { text-decoration:underline; }
    </style>
</head>
<body>
<jsp:include page="sidebar.jsp" />
<div class="card">
    <h2>Ajouter un projet</h2>
    <form>
        <div class="mb-3">
            <label for="nomProjet" class="form-label">Nom du projet</label>
            <input type="text" class="form-control" id="nomProjet" placeholder="Ex: Migration Système">
        </div>
        <div class="mb-3">
            <label for="responsable" class="form-label">Responsable du projet</label>
            <input type="text" class="form-control" id="responsable" placeholder="Nom du responsable">
        </div>
        <div class="mb-3">
            <label for="description" class="form-label">Description</label>
            <textarea class="form-control" id="description" rows="3" placeholder="Brève description du projet"></textarea>
        </div>
        <div class="mb-3">
            <label for="dateDebut" class="form-label">Date de début</label>
            <input type="date" class="form-control" id="dateDebut">
        </div>
        <div class="mb-3">
            <label for="dateFin" class="form-label">Date de fin</label>
            <input type="date" class="form-control" id="dateFin">
        </div>
        <button type="submit" class="btn btn-primary w-100">Ajouter</button>
    </form>
</div>
</body>
</html>
