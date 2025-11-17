<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accueil RH</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            font-family: Arial, sans-serif;
        }
        .card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            text-align: center;
            width: 100%;
            max-width: 500px;
        }
        .card h1 {
            color: #2a5298;
            margin-bottom: 1.5rem;
        }
        .btn-primary {
            background-color: #2a5298;
            border: none;
            border-radius: 8px;
            font-weight: bold;
            padding: 0.75rem 1.5rem;
            font-size: 1.1rem;
        }
        .btn-primary:hover {
            background-color: #1e3c72;
        }
    </style>
</head>
<body>
<div class="card">
    <h1>Bienvenue sur le portail RH</h1>
    <p>Accédez directement au tableau de bord pour gérer les employés, départements et projets.</p>
    <a href="dashboardRH.jsp" class="btn btn-primary">Accéder au tableau de bord RH</a>
</div>
</body>
</html>
