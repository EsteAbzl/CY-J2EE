<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Payslip" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des fiches de paie</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        h2 { text-align:center; color:#fff; margin-bottom:1rem; }
        table { width:100%; border-collapse:collapse; margin:auto; background:#fff; border-radius:12px; overflow:hidden; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        th, td { padding:.75rem; border:1px solid #ddd; text-align:left; }
        th { background:#2a5298; color:#fff; }
        tr:nth-child(even) { background:#f9f9f9; }
        .btn { display:inline-block; background:#2a5298; color:#fff; text-decoration:none; padding:.5rem 1rem; border-radius:8px; font-weight:bold; margin:.25rem; transition:.3s; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h2>Liste des fiches de paie</h2>
<table>
    <tr>
        <th>ID</th>
        <th>ID Employé</th>
        <th>Période</th>
        <th>Net à payer</th>
        <th>Actions</th>
    </tr>
    <%
        List<Payslip> payslips = (List<Payslip>) request.getAttribute("payslips");
        if (payslips != null) {
            for (Payslip p : payslips) {
    %>
    <tr>
        <td><%= p.getId() %></td>
        <td><%= p.getEmployeeId() %></td>
        <td><%= p.getPeriodMonth() %>/<%= p.getPeriodYear() %></td>
        <td><%= p.getNetPay() %></td>
        <td>
            <a class="btn" href="PayslipPrintServlet?id=<%= p.getId() %>">🖨️ Imprimer</a>
        </td>
    </tr>
    <% } } %>
</table>
<div style="text-align:center;margin-top:1rem;">
    <a class="btn" href="generatePayslip.jsp">➕ Générer une fiche de paie</a>
    <a class="btn" href="dashboard.jsp">🏠 Retour au tableau de bord</a>
</div>
</body>
</html>
