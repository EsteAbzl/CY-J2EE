<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Impression fiche de paie</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg,#1e3c72,#2a5298); /* même fond que les autres pages */
            margin: 0;
            padding: 40px;
            color: #2c3e50;
            min-height: 100vh;
        }
        header {
            text-align: center;
            margin-bottom: 30px;
            background:#2c3e50;
            color:#fff;
            padding:15px;
            border-radius:8px;
            box-shadow:0 4px 10px rgba(0,0,0,0.3);
        }
        header h1 {
            margin: 0;
            font-size: 24px;
        }
        .payslip {
            width: 80%;
            margin: 0 auto;
            border: 1px solid #ddd;
            padding: 20px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            background-color: #fff; /* fiche sur fond blanc pour impression */
            border-radius:8px;
        }
        .payslip h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #2c3e50;
        }
        .payslip table {
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        .payslip th, .payslip td {
            padding: 10px;
            border: 1px solid #ddd;
            text-align: left;
        }
        .payslip th {
            background-color: #34495e;
            color: #fff;
        }
        .summary {
            text-align: right;
            font-size: 1.1em;
            margin-top: 20px;
        }
        .print-btn {
            display: block;
            margin: 20px auto;
            padding: 10px 20px;
            background-color: #27ae60; /* vert harmonisé */
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 1em;
            font-weight:bold;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        .print-btn:hover {
            background-color: #219150;
        }
        a {
            display:block;
            width:max-content;
            margin:20px auto;
            padding: 10px 20px;
            text-decoration: none;
            background-color: #27ae60;
            color: #fff;
            border-radius: 4px;
            font-weight: bold;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        a:hover {
            background-color:#219150;
        }
    </style>
</head>
<body onload="window.print()">
<header>
    <h1>Entreprise</h1>
    <p>Fiche de paie officielle</p>
</header>

<div class="payslip">
    <h2>Fiche de paie - ${payslip.periodMonth}/${payslip.periodYear}</h2>
    <p style="text-align:right; color:#2c3e50;"><strong>Généré le: ${payslip.generatedAt}</strong></p>
    <table>
        <tr>
            <th>Nom et Prénom</th>
            <td>${employee.firstName} ${employee.lastName}</td>
        </tr>
        <tr>
            <th>Salaire de base</th>
            <td>${payslip.baseSalary} €</td>
        </tr>
        <tr>
            <th>Bonus</th>
            <td>
                <c:forEach var="extra" items="${extras}">
                    <c:if test="${extra.montant > 0}">
                        ${extra.montant}€: ${extra.motif}<br>
                    </c:if>
                </c:forEach>
                <c:if test="${payslip.bonuses > 0}">
                    <strong>Total Bonus: ${payslip.bonuses} €</strong>
                </c:if>
            </td>
        </tr>
        <tr>
            <th>Malus / Déductions</th>
            <td>
                <c:forEach var="extra" items="${extras}">
                    <c:if test="${extra.montant < 0}">
                        ${-extra.montant}€: ${extra.motif}<br>
                    </c:if>
                </c:forEach>
                <c:if test="${payslip.deductions > 0}">
                    <strong>Total Déductions: ${payslip.deductions} €</strong>
                </c:if>
            </td>
        </tr>
        <tr>
            <th>Net à payer</th>
            <td><strong>${payslip.netPay} €</strong></td>
        </tr>
    </table>

    <div class="summary">
        <p><strong>ID Fiche: ${payslip.id}</strong></p>
    </div>
</div>

<button class="print-btn" onclick="window.print()">🖨️ Imprimer</button>
<a href="dashboard.jsp">Retour au tableau de bord</a>
</body>
</html>
