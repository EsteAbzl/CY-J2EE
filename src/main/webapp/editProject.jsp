<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un projet</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            min-height: 100vh;
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        .card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 600px;
        }
        .card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2a5298;
        }
    </style>
</head>
<body>
<div class="card">
    <h2>Modifier un projet</h2>
    <form action="EditProjectServlet" method="post">
        <input type="hidden" name="id" value="${projet.id}" />

        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" name="nom" value="${projet.nom}" required>
        </div>

        <div class="mb-3">
            <label for="avancement" class="form-label">Avancement</label>
            <input type="text" class="form-control" id="avancement" name="avancement" value="${projet.avancement}">
        </div>

        <button type="submit" class="btn btn-primary">Enregistrer</button>
        <a href="ProjectsListServlet" class="btn btn-secondary">Annuler</a>
    </form>
</div>
</body>
</html>
