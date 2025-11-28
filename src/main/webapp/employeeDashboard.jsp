<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model.Employee, java.util.List, com.model.Payslip" %>
<%
    Employee emp = (Employee) request.getAttribute("employee");
    List<Payslip> payslips = (List<Payslip>) request.getAttribute("payslips");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Dashboard Employé</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h1 { text-align:center; color:#fff; }
        .card { background:#fff; border-radius:12px; padding:1.5rem; margin:1rem auto; max-width:700px; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        table { width:100%; border-collapse:collapse; margin-top:1rem; }
        th, td { border:1px solid #ccc; padding:.5rem; text-align:left; }
        th { background:#eee; }
        .btn { display:inline-block; background:#2a5298; color:#fff; padding:.5rem 1rem; border-radius:8px; text-decoration:none; margin-top:1rem; }
        .btn:hover { background:#1e3c72; }
        a { background:#27ae60; color:#fff; border:none; padding:8px 16px; border-radius:4px; font-weight:bold; cursor:pointer; text-decoration: none; display:inline-block; }
        a:hover { background:#229954; }
    </style>
</head>
<body>
<div class="logout">
    <jsp:include page="LogoutButton.jsp" />
</div>
<h1>Dashboard Employé</h1>

<c:if test="${not empty emp}">
    <div class="card">
        <h2>Mon profil</h2>
        <p><strong>Nom :</strong> ${emp.firstName} ${emp.lastName}</p>
        <p><strong>Email :</strong> ${emp.email}</p>
        <p><strong>Poste :</strong> ${emp.positionTitle}</p>
        <p><strong>Département :</strong> ${emp.departmentId}</p>
    </div>
</c:if>

<div class="card">
    <h2>Mes fiches de paie</h2>
    <table>
        <tr><th>Période</th><th>Salaire net</th><th>Date génération</th><th>Action</th></tr>
        <% if (payslips != null) {
            for (Payslip ps : payslips) { %>
        <tr>
            <td><%= ps.getPeriodMonth() %>/<%= ps.getPeriodYear() %></td>
            <td><%= ps.getNetPay() %> €</td>
            <td><%= ps.getGeneratedAt() %></td>
            <td><a href="PayslipPrintServlet?id=<%=ps.getId()%>">Consulter</a></td>
        </tr>
        <% } } %>
    </table>
</div>

<div class="card">
    <h2>Mes absences</h2>
    <a class="btn" href="AbsenceServlet">Voir mes absences</a>
</div>
</body>
</html>
