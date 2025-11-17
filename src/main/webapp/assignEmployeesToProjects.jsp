<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Affecter un employé à des projets</title>
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
            width: 100%;
            max-width: 500px;
        }
        .card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2a5298;
        }
        .form-control, .form-select {
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
<div class="card">
    <h2>Affecter un employé à des projets</h2>
    <form>
        <div class="mb-3">
            <label for="employee" class="form-label">Employé</label>
            <select class="form-select" id="employee">
                <option>Marie Dupont</option>
                <option>Jean Martin</option>
                <option>Sophie Leroy</option>
                <option>Paul Durand</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="projects" class="form-label">Projets</label>
            <select multiple class="form-select" id="projects">
                <option>Migration Système</option>
                <option>Application Mobile</option>
                <option>Campagne Marketing</option>
                <option>Audit Financier</option>
            </select>
            <small class="text-muted">Maintenez CTRL (ou CMD sur Mac) pour sélectionner plusieurs projets.</small>
        </div>

        <button type="submit" class="btn btn-primary w-100">Affecter</button>
    </form>
    <div class="footer">
        <a href="dashboardRH.jsp">Retour au tableau de bord RH</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
