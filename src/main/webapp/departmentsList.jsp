<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg,#1e3c72,#2a5298);
            margin: 0;
            padding: 0;
            min-height: 100vh;
            color: #2c3e50;
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
            color: #fff;
        }
        table {
            border-collapse: collapse;
            width: 90%;
            margin: 20px auto;
            background-color: #fff;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            border-radius: 8px;
            overflow: hidden;
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
        .view-btn {
            background-color: #2ecc71;
        }
        .edit-btn {
            background-color: #3498db;
        }
        .delete-btn {
            background-color: #e74c3c;
        }
        .add-link {
            display: block;
            text-align: center;
            margin: 20px;
        }
        .add-link a {
            text-decoration: none;
            background-color: #27ae60;
            color: #fff;
            padding: 8px 16px;
            border-radius: 4px;
            font-weight: bold;
        }
    </style>
</head>
<body>
<header>
    <h1>Gestion des départements</h1>
</header>

<h2>Liste des départements</h2>

<c:if test="${empty departments}">
    <p style="text-align:center;">Aucun département trouvé.</p>
</c:if>

<c:if test="${not empty departments}">
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Nom</th>
            <th>Description</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="dept" items="${departments}">
            <tr>
                <td>${dept.id}</td>
                <td>${dept.name}</td>
                <td>${dept.description}</td>
                <td class="actions">
                    <a href="DepartmentMembersServlet?id=${dept.id}" class="view-btn">Voir membres</a>
                    <a href="DepartmentEditServlet?id=${dept.id}" class="edit-btn">Modifier</a>
                    <a href="DepartmentDeleteServlet?id=${dept.id}" class="delete-btn"
                       onclick="return confirm('Supprimer ce département ?');">Supprimer</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>

<div class="add-link">
    <a href="addDepartment.jsp">+ Ajouter un département</a>
    <a href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
