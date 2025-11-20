<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            min-height: 100vh;
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
        }
        .sidebar {
            width: 250px;
            background-color: #f8f9fa;
            min-height: 100vh;
            box-shadow: 2px 0 5px rgba(0,0,0,0.1);
        }
        .main-content {
            flex-grow: 1;
            padding: 2rem;
        }
        .card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 1000px;
            margin: auto;
        }
        .card h2 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2a5298;
        }
        .table thead {
            background-color: #2a5298;
            color: #fff;
        }
    </style>
</head>
<body>
<div class="sidebar">
    <jsp:include page="sidebar.jsp" />
</div>

<div class="main-content">
    <div class="card">
        <h2>Liste des employés</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Prénom</th>
                <th>Email</th>
                <th>Poste</th>
                <th>Grade</th>
                <th>ID Departement</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="emp" items="${employes}">
                <tr>
                    <td>${emp.id}</td>
                    <td>${emp.nom}</td>
                    <td>${emp.prenom}</td>
                    <td>${emp.email}</td>
                    <td>${emp.poste}</td>
                    <td>${emp.grade}</td>
                    <td>${emp.idDepartement}</td>
                    <td>
                        <a href="EditEmployeeServlet?id=${emp.id}" class="btn btn-warning btn-sm">Modifier</a>

                        <a href="DeleteEmployeeServlet?id=${emp.id}"
                           class="btn btn-danger btn-sm"
                           onclick="return confirm('Voulez-vous vraiment supprimer cet employé ?');">
                            Supprimer
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
