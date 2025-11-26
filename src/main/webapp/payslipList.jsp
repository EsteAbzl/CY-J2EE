<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
    <style>
        body { font-family: Arial, sans-serif; background:linear-gradient(135deg,#1e3c72,#2a5298); margin:0; padding:0; min-height:100vh; }
        header { background:#2c3e50; color:#fff; padding:15px; text-align:center; }
        h2 { text-align:center; color:#fff; margin:20px 0; }
        form { background:#fff; padding:1rem; border-radius:8px; max-width:800px; margin:20px auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); display:flex; justify-content:center; gap:15px; flex-wrap:wrap; }
        label { font-weight:bold; }
        input, select { padding:5px; }
        button { background:#27ae60; color:#fff; border:none; padding:8px 16px; border-radius:4px; font-weight:bold; cursor:pointer; }
        table { border-collapse:collapse; width:90%; margin:20px auto; background:#fff; box-shadow:0 8px 20px rgba(0,0,0,0.25); border-radius:8px; overflow:hidden; }
        th, td { border:1px solid #ddd; padding:10px 15px; text-align:left; }
        th { background:#34495e; color:#fff; }
        tr:nth-child(even) { background:#f2f2f2; }
        a { background:#27ae60; color:#fff; border:none; padding:8px 16px; border-radius:4px; font-weight:bold; cursor:pointer; text-decoration: none; }
    </style>
</head>
<body>
<header>
    <h1>Fiches de paie</h1>
</header>

<h2>Filtrer les fiches de paie</h2>
<form action="PayslipListServlet" method="get">
    <label>Employé :</label>
    <input type="number" name="employee_id" placeholder="ID employé">

    <label>Mois :</label>
    <select name="month">
        <option value="">--</option>
        <c:forEach var="m" begin="1" end="12">
            <option value="${m}">${m}</option>
        </c:forEach>
    </select>

    <label>Année :</label>
    <input type="number" name="year" placeholder="2025">

    <button type="submit">Filtrer</button>
</form>

<h2>Liste des fiches de paie</h2>
<table>
    <tr>
        <th>ID</th>
        <th>ID Employé</th>
        <th>Mois</th>
        <th>Année</th>
        <th>Salaire de base</th>
        <th>Bonus</th>
        <th>Déductions</th>
        <th>Net</th>
        <th>Généré le</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="p" items="${payslips}">
        <tr>
            <td>${p.id}</td>
            <td>${p.employeeId}</td>
            <td>${p.periodMonth}</td>
            <td>${p.periodYear}</td>
            <td>${p.baseSalary}</td>
            <td>${p.bonuses}</td>
            <td>${p.deductions}</td>
            <td>${p.netPay}</td>
            <td>${p.generatedAt}</td>
            <td><a href="PayslipPrintServlet?id=${p.id}">Imprimer</a></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align:center;margin-top:1rem;">
    <a class="btn" href="generatePayslip.jsp"> Générer une fiche de paie</a>
    <a class="btn" href="dashboard.jsp"> Retour au tableau de bord</a>
</div>
</body>
</html>
