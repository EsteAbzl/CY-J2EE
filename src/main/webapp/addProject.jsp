<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un projet</title>
    <style>
        body {
            background: linear-gradient(135deg,#1e3c72,#2a5298);
            font-family: Arial, sans-serif;
            padding: 2rem;
            margin: 0;
            min-height: 100vh;
        }
        h2 { text-align: center; color: #fff; }
        form {
            background: #fff;
            border-radius: 12px;
            padding: 2rem;
            max-width: 600px;
            margin: auto;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
        }
        label {
            display: block;
            margin-top: 1rem;
            font-weight: bold;
            color: #2a5298;
        }
        input, textarea, select {
            width: 100%;
            padding: .5rem;
            border: 1px solid #ccc;
            border-radius: 8px;
            margin-top: .25rem;
            box-sizing: border-box;
        }
        textarea { resize: vertical; min-height: 80px; }
        .btn {
            background: #2a5298;
            color: #fff;
            border: none;
            padding: .75rem;
            border-radius: 8px;
            font-weight: bold;
            margin-top: 1.5rem;
            cursor: pointer;
            width: 100%;
        }
        .btn:hover { background: #1e3c72; }
        .actions {
            max-width: 600px;
            margin: 1rem auto;
            display: flex;
            gap: 1rem;
            justify-content: center;
        }
        .link {
            color: #fff;
            border: 1px solid #fff;
            padding: .5rem 1rem;
            border-radius: 8px;
            text-decoration: none;
        }
    </style>
</head>
<body>
<h2>Ajouter un projet</h2>

<form action="ProjectCreateServlet" method="post">
    <label>Nom du projet</label>
    <input type="text" name="name" required>

    <label>Description</label>
    <textarea name="description"></textarea>

    <label>Date de début</label>
    <input type="date" name="start_date" required>

    <label>Date de fin</label>
    <input type="date" name="end_date" required>

    <label>Département associé</label>
    <input type="number" name="department_id" required>

    <label>Status :</label>
    <select name="status" required>
        <option value="EN_COURS">En cours</option>
        <option value="ANNULE">Annulé</option>
        <option value="TERMINE">Terminé</option>
    </select>

    <button class="btn" type="submit">Créer le projet</button>
</form>

<div class="actions">
    <a class="link" href="ProjectsListServlet">Voir les projets</a>
    <a class="link" href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
