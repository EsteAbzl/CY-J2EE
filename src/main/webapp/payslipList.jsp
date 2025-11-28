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
        .search-container {
            margin: 30px auto;
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
            padding: 30px;
            border-radius: 12px;
            max-width: 950px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.12), 0 2px 4px rgba(0,0,0,0.08);
            border: 1px solid #e8ebed;
        }
        .search-container h2 {
            margin: 0 0 25px 0;
            font-size: 22px;
            color: #2c3e50;
            text-align: center;
        }
        .search-bar {
            display: flex;
            justify-content: center;
            gap: 12px;
            flex-wrap: wrap;
            align-items: center;
        }
        .search-bar input, .search-bar select {
            padding: 0.75rem 0.9rem;
            border: 2px solid #e1e5e8;
            border-radius: 6px;
            font-size: 14px;
            background: #fff;
            transition: all 0.3s ease;
        }
        .search-bar input:focus, .search-bar select:focus {
            outline: none;
            border-color: #27ae60;
            box-shadow: 0 0 0 3px rgba(39, 174, 96, 0.1);
        }
        .search-bar input[type="text"] {
            min-width: 200px;
            flex: 1;
            min-width: 150px;
        }
        .search-bar input[type="number"] {
            width: 110px;
        }
        .search-bar select {
            width: 90px;
        }
        .search-bar button {
            padding: 0.75rem 1.5rem;
            border: none;
            background: linear-gradient(135deg, #27ae60 0%, #229954 100%);
            color: white;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(39, 174, 96, 0.3);
        }
        .search-bar button:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(39, 174, 96, 0.4);
            background: linear-gradient(135deg, #229954 0%, #1e8449 100%);
        }
        .search-bar button.reset {
            background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%);
            box-shadow: 0 4px 12px rgba(149, 165, 166, 0.3);
        }
        .search-bar button.reset:hover {
            background: linear-gradient(135deg, #7f8c8d 0%, #697174 100%);
            box-shadow: 0 6px 16px rgba(149, 165, 166, 0.4);
        }
        .search-label {
            font-weight: 600;
            color: #2c3e50;
            white-space: nowrap;
            font-size: 14px;
        }
        table { border-collapse:collapse; width:90%; margin:20px auto; background:#fff; box-shadow:0 8px 20px rgba(0,0,0,0.25); border-radius:8px; overflow:hidden; }
        th, td { border:1px solid #ddd; padding:10px 15px; text-align:left; }
        th { background:#34495e; color:#fff; }
        tr:nth-child(even) { background:#f2f2f2; }
        a { background:#27ae60; color:#fff; border:none; padding:8px 16px; border-radius:4px; font-weight:bold; cursor:pointer; text-decoration: none; display:inline-block; }
        a:hover { background:#229954; }
        .actions { text-align:center; margin-top:1rem; }
    </style>
</head>
<body>
<header>
    <h1>Fiches de paie</h1>
</header>

<div class="search-container">
    <h2>Rechercher une fiche de paie</h2>
    <form action="PayslipListServlet" method="get" class="search-bar">
        <input type="text" name="query" placeholder="Nom, prénom ou ID employé">
        
        <span class="search-label">Mois :</span>
        <select name="month">
            <option value="">--</option>
            <c:forEach var="m" begin="1" end="12">
                <option value="${m}" <c:if test="${month == m}">selected</c:if>>${m}</option>
            </c:forEach>
        </select>

        <span class="search-label">Année :</span>
        <input type="number" name="year" placeholder="2025" value="${year != null ? year : ''}">

        <button type="submit">🔍 Rechercher</button>
        <a href="PayslipListServlet" style="background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%); color: white; border: none; padding: 0.75rem 1.5rem; border-radius: 6px; font-weight: 600; cursor: pointer; text-decoration: none; box-shadow: 0 4px 12px rgba(149, 165, 166, 0.3); transition: all 0.3s ease; display: inline-block; margin-left: 8px;">↺ Réinitialiser</a>
    </form>
</div>

<h2>Liste des fiches de paie</h2>
<table>
    <tr>
        <th>ID Fiche</th>
        <th>ID Employé</th>
        <th>Nom</th>
        <th>Année</th>
        <th>Mois</th>
        <th>Généré le</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="p" items="${payslips}">
        <tr>
            <td>${p.id}</td>
            <td>${p.employeeId}</td>
            <td>${employeeNames[p.employeeId]}</td>
            <td>${p.periodYear}</td>
            <td>${p.periodMonth}</td>
            <td>${p.generatedAt}</td>
            <td><a href="PayslipPrintServlet?id=${p.id}">Consulter</a></td>
        </tr>
    </c:forEach>
</table>
<div style="text-align:center;margin-top:1rem;">
    <a class="btn" href="generatePayslip.jsp"> Générer une fiche de paie</a>
    <a class="btn" href="dashboard.jsp"> Retour au tableau de bord</a>
</div>
</body>
</html>
