<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Absence, com.model.Employee" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Consultation des absences</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h1 { text-align:center; color:#fff; }
        .card { background:#fff; border-radius:12px; padding:1.5rem; margin:1rem auto; max-width:900px; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        table { width:100%; border-collapse:collapse; margin-top:1rem; }
        th, td { border:1px solid #ccc; padding:.5rem; text-align:left; }
        th { background:#eee; }
        .btn { display:inline-block; background:#27ae60; color:#fff; padding:.5rem 1rem; border-radius:8px; text-decoration:none; margin-top:1rem; cursor: pointer; }
        .btn:hover { background:#219150; }
        label { font-weight:bold; color:#2a5298; }
        select { padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; width:100%; }
    </style>
</head>
<body>
<h1>Consultation des absences</h1>

<div class="card">
    <h2>Sélectionner un employé</h2>
    <form action="AbsenceListServlet" method="get">
        <label for="employeeId">Employé :</label>
        <select name="employeeId" id="employeeId" required>
            <option value="">-- Choisir un employé --</option>
            <!-- Liste des employés alimentée par le servlet -->
            <c:forEach var="emp" items="${employees}">
                <option value="${emp.id}" <c:if test="${selectedEmployeeId == emp.id}">selected</c:if>>
                        ${emp.firstName} ${emp.lastName} (${emp.email})
                </option>
            </c:forEach>
        </select>
        <button class="btn" type="submit">Voir les absences</button>
    </form>
</div>

<div class="card">
    <h2>Historique des absences</h2>
    <c:if test="${not empty absences}">
        <table>
            <tr>
                <th>Date</th>
                <th>Type</th>
                <th>Heures</th>
            </tr>
            <c:forEach var="a" items="${absences}">
                <tr>
                    <td>${a.date}</td>
                    <td>${a.type}</td>
                    <td>${a.hours}</td>
                </tr>
            </c:forEach>
        </table>
    </c:if>
    <c:if test="${empty absences}">
        <p>Aucune absence enregistrée pour cet employé.</p>
    </c:if>
</div>

<div class="card">
    <a class="btn" href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
