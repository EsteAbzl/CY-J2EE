<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Membres du département</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 0;
        }
        header {
            background-color: #2c3e50;
            color: #fff;
            padding: 15px;
            text-align: center;
        }
        h2 {
            margin: 20px 0;
            text-align: center;
            color: #2c3e50;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 10px 15px;
            text-align: left;
        }
        th {
            background-color: #34495e;
            color: #fff;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        .actions a {
            display: inline-block;
            padding: 6px 12px;
            margin-right: 5px;
            text-decoration: none;
            color: #fff;
            border-radius: 4px;
            font-size: 0.9em;
        }
        .edit-btn {
            background-color: #3498db;
        }
        .delete-btn {
            background-color: #e74c3c;
        }
        .back-link {
            display: block;
            text-align: center;
            margin: 20px;
        }
        .back-link a {
            text-decoration: none;
            color: #2c3e50;
            font-weight: bold;
        }
    </style>
</head>
<body>
<header>
    <h1>Gestion des départements</h1>
</header>

<h2>Membres du département</h2>

<c:if test="${empty employees}">
    <p style="text-align:center;">Aucun employé trouvé pour ce département.</p>
</c:if>

<c:if test="${not empty employees}">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Prénom</th>
            <th>Nom</th>
            <th>Email</th>
            <th>Grade</th>
            <th>Poste</th>
            <th>Salaire</th>
            <th>Actif</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="emp" items="${employees}">
            <tr>
                <td>${emp.id}</td>
                <td>${emp.firstName}</td>
                <td>${emp.lastName}</td>
                <td>${emp.email}</td>
                <td>${emp.grade}</td>
                <td>${emp.positionTitle}</td>
                <td>${emp.baseSalary}</td>
                <td>
                    <c:choose>
                        <c:when test="${emp.active}">Oui</c:when>
                        <c:otherwise>Non</c:otherwise>
                    </c:choose>
                </td>
                <td class="actions">
                    <a href="EmployeeEditServlet?id=${emp.id}" class="edit-btn">Modifier</a>
                    <a href="EmployeeDeleteServlet?id=${emp.id}" class="delete-btn"
                       onclick="return confirm('Supprimer cet employé ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<div class="back-link">
    <a href="DepartmentsListServlet">← Retour à la liste</a>
</div>
</body>
</html>
