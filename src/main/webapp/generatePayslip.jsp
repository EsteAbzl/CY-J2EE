<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Générer une fiche de paie</title>
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
            max-width: 600px;
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
    <h2>Générer une fiche de paie</h2>
    <!-- Formulaire unique -->
    <form action="GeneratePayslipServlet" method="post">
        <div class="mb-3">
            <label for="employee" class="form-label">Employé</label>
            <select class="form-select" id="employee" name="employee">
                <option>Marie Dupont</option>
                <option>Jean Martin</option>
                <option>Sophie Leroy</option>
                <option>Paul Durand</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="month" class="form-label">Mois</label>
            <select class="form-select" id="month" name="month">
                <option>Janvier</option>
                <option>Février</option>
                <option>Mars</option>
                <option>Avril</option>
                <option>Mai</option>
                <option>Juin</option>
                <option>Juillet</option>
                <option>Août</option>
                <option>Septembre</option>
                <option>Octobre</option>
                <option>Novembre</option>
                <option>Décembre</option>
            </select>
        </div>

        <div class="mb-3">
            <label for="year" class="form-label">Année</label>
            <input type="number" class="form-control" id="year" name="year" placeholder="2025">
        </div>

        <div class="mb-3">
            <label for="baseSalary" class="form-label">Salaire de base (€)</label>
            <input type="number" class="form-control" id="baseSalary" name="baseSalary" placeholder="Ex: 2500">
        </div>
        <div class="mb-3">
            <label for="bonus" class="form-label">Prime (€)</label>
            <input type="number" class="form-control" id="bonus" name="bonus" placeholder="Ex: 300">
        </div>
        <div class="mb-3">
            <label for="deductions" class="form-label">Déductions (€)</label>
            <input type="number" class="form-control" id="deductions" name="deductions" placeholder="Ex: 150">
        </div>

        <button type="submit" class="btn btn-primary w-100">Générer la fiche de paie</button>
    </form>

    <div class="footer">
        <a href="dashboardRH.jsp">Retour au tableau de bord RH</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
