<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Absence" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    List<Absence> absences = (List<Absence>) request.getAttribute("absences");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Mes absences</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h1 { text-align:center; color:#fff; }
        .card { background:#fff; border-radius:12px; padding:1.5rem; margin:1rem auto; max-width:800px; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        table { width:100%; border-collapse:collapse; margin-top:1rem; }
        th, td { border:1px solid #ccc; padding:.5rem; text-align:left; }
        th { background:#eee; }
        .btn { display:inline-block; background:#2a5298; color:#fff; padding:.5rem 1rem; border-radius:8px; text-decoration:none; margin-top:1rem; }
        .btn:hover { background:#1e3c72; cursor: pointer; }
        form { margin-top:1rem; }
        label { display:block; margin-top:.5rem; font-weight:bold; color:#2a5298; }
        input, select { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; }
    </style>
</head>
<body>
<div class="logout">
    <jsp:include page="LogoutButton.jsp" />
</div>
<h1>Mes absences</h1>

<div style="text-align: center">

    <div class="card">
        <h2>Historique des absences</h2>

        <c:if test="${not empty absences}">
            <table border="1" cellpadding="5" cellspacing="0">
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
            <p>Aucune absence enregistrée.</p>
        </c:if>
    </div>


    <div class="card">
        <h2>Déclarer une absence</h2>
        <form action="AbsenceServlet" method="post">
            <label>Date</label>
            <input type="date" name="date" required>

            <label>Type</label>
            <select name="type" required>
                <option value="CONGE">Congé</option>
                <option value="MALADIE">Maladie</option>
                <option value="NON_PAYE">Non payé</option>
            </select>

            <label>Nombre d’heures</label>
            <input type="number" name="hours" value="8" min="1" max="24">

            <button class="btn" type="submit">➕ Ajouter</button>
        </form>
    </div>

    <a class="btn" href="EmployeeDashboardServlet"> Retour a votre Dashboard</a>
</div>
</body>
</html>
