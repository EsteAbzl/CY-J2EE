<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un employé</title>
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
        .form-card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 500px;
        }
        .form-card h2 {
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
        .footer {
            text-align: center;
            margin-top: 1rem;
            font-size: 0.9rem;
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
<div class="form-card">
    <h2>Ajouter un employé</h2>
    <form>
        <div class="mb-3">
            <label for="nom" class="form-label">Nom</label>
            <input type="text" class="form-control" id="nom" placeholder="Nom de famille">
        </div>
        <div class="mb-3">
            <label for="prenom" class="form-label">Prénom</label>
            <input type="text" class="form-control" id="prenom" placeholder="Prénom">
        </div>
        <div class="mb-3">
            <label for="email" class="form-label">Adresse email</label>
            <input type="email" class="form-control" id="email" placeholder="exemple@entreprise.com">
        </div>
        <div class="mb-3">
            <label for="poste" class="form-label">Poste</label>
            <input type="text" class="form-control" id="poste" placeholder="Intitulé du poste">
        </div>
        <div class="mb-3">
            <label for="departement" class="form-label">Département</label>
            <select class="form-control" id="departement">
                <option>Ressources Humaines</option>
                <option>Finance</option>
                <option>Informatique</option>
                <option>Marketing</option>
                <option>Production</option>
            </select>
        </div>
        <div class="mb-3">
            <label for="dateEmbauche" class="form-label">Date d’embauche</label>
            <input type="date" class="form-control" id="dateEmbauche">
        </div>
        <button type="submit" class="btn btn-primary w-100">Ajouter l’employé</button>
    </form>
    <div class="footer">
        <a href="dashboardRH.jsp">Retour au tableau de bord RH</a>
    </div>
</div>
</body>
</html>
