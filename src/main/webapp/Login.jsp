<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Connexion</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
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
        .login-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 400px;
        }
        .login-card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2a5298;
        }
        .form-control {
            border-radius: 8px;
        }
        .btn-primary {
            background-color: #2a5298;
            border: none;
            border-radius: 8px;
            font-weight: bold;
        }
        .btn-primary:hover {
            background-color: #1e3c72;
        }
        .footer a {
            color: #2a5298;
            text-decoration: none;
        }
        .footer a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="login-card">
    <h2>Connexion</h2>
    <form action="${pageContext.request.contextPath}/login" method="post">
        <div class="mb-3">
            <label for="username" class="form-label">Identifiant</label>
            <input type="text" name="email" class="form-control" id="username" placeholder="Identifiant">
        </div>
        <div class="mb-3">
            <label for="password" class="form-label">Mot de passe</label>
            <input type="password" name="mdp" class="form-control" id="password" placeholder="Mot de passe">
        </div>
        <button type="submit" class="btn btn-primary w-100">Se connecter</button>
        <br/>
        <br/>
        <div class="text-center text-danger">
            <% String error = request.getParameter("error");
               if ("missing".equals(error)) { %>
                   Merci de remplir tous les champs.
            <% } else if ("notfound".equals(error)) { %>
                   Utilisateur introuvable.
            <% } else if ("badcreds".equals(error)) { %>
                   Identifiants incorrects.
            <% } %>
        </div>
    </form>
</div>
</body>
</html>
