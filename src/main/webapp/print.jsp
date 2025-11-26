<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model.Payslip" %>
<%
    Payslip payslip = (Payslip) request.getAttribute("payslip");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Fiche de paie</title>
    <style>
        body {
            font-family: "Times New Roman", serif;
            background:#fff;
            margin:2rem;
            color:#000;
        }
        h1 {
            text-align:center;
            font-size:1.8rem;
            margin-bottom:2rem;
        }
        table {
            width:100%;
            border-collapse:collapse;
            margin-bottom:2rem;
        }
        th, td {
            border:1px solid #000;
            padding:.6rem;
            text-align:left;
        }
        th {
            background:#eee;
        }
        .signature {
            margin-top:3rem;
            display:flex;
            justify-content:space-between;
        }
        .signature div {
            width:45%;
            text-align:center;
            border-top:1px solid #000;
            padding-top:.5rem;
        }
        @media print {
            .no-print { display:none; }
        }
        .no-print {
            text-align:center;
            margin-top:1rem;
        }
        .btn {
            display:inline-block;
            background:#2a5298;
            color:#fff;
            text-decoration:none;
            padding:.5rem 1rem;
            border-radius:6px;
            font-weight:bold;
            margin:.25rem;
        }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h1>Fiche de paie</h1>
<% if (payslip != null) { %>
<table>
    <tr><th>ID</th><td><%= payslip.getId() %></td></tr>
    <tr><th>ID Employé</th><td><%= payslip.getEmployeeId() %></td></tr>
    <tr><th>Période</th><td><%= payslip.getPeriodMonth() %>/<%= payslip.getPeriodYear() %></td></tr>
    <tr><th>Salaire de base</th><td><%= payslip.getBaseSalary() %> €</td></tr>
    <tr><th>Primes</th><td><%= payslip.getBonuses() %> €</td></tr>
    <tr><th>Déductions</th><td><%= payslip.getDeductions() %> €</td></tr>
    <tr><th>Net à payer</th><td><strong><%= payslip.getNetPay() %> €</strong></td></tr>
</table>

<div class="signature">
    <div>Signature employé</div>
    <div>Signature RH</div>
</div>
<% } else { %>
<p style="color:red;text-align:center;">Aucune fiche de paie à afficher.</p>
<% } %>

<div class="no-print">
    <a class="btn" href="javascript:window.print()">🖨️ Imprimer / Exporter PDF</a>
    <a class="btn" href="payslips">⬅️ Retour à la liste</a>
</div>
</body>
</html>
