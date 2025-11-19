<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Aperçu de la fiche de paie</title>
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
            max-width: 700px;
        }
        .card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2a5298;
        }
        .table thead {
            background-color: #2a5298;
            color: #fff;
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
    <h2>Aperçu de la fiche de paie</h2>
    <table class="table table-bordered">
        <tbody>
        <tr><th>Employé</th><td><%= request.getAttribute("employee") %></td></tr>
        <tr><th>Période</th><td><%= request.getAttribute("month") %> <%= request.getAttribute("year") %></td></tr>
        <tr><th>Salaire de base</th><td><%= request.getAttribute("baseSalary") %> €</td></tr>
        <tr><th>Prime</th><td><%= request.getAttribute("bonus") %> €</td></tr>
        <tr><th>Déductions</th><td><%= request.getAttribute("deductions") %> €</td></tr>
        <tr class="table-success"><th>Salaire net</th><td><%= request.getAttribute("netSalary") %> €</td></tr>
        </tbody>
    </table>

    <div class="d-flex justify-content-between mt-3">
        <button class="btn btn-primary" onclick="window.print()">Imprimer</button>
        <!-- Formulaire caché pour exporter en PDF -->
        <form action="ExportPayslipServlet" method="post">
            <input type="hidden" name="employee" value="<%= request.getAttribute("employee") %>">
            <input type="hidden" name="month" value="<%= request.getAttribute("month") %>">
            <input type="hidden" name="year" value="<%= request.getAttribute("year") %>">
            <input type="hidden" name="baseSalary" value="<%= request.getAttribute("baseSalary") %>">
            <input type="hidden" name="bonus" value="<%= request.getAttribute("bonus") %>">
            <input type="hidden" name="deductions" value="<%= request.getAttribute("deductions") %>">
            <input type="hidden" name="netSalary" value="<%= request.getAttribute("netSalary") %>">
            <button type="submit" class="btn btn-primary">Exporter en PDF</button>
        </form>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
