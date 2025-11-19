<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des départements</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            min-height: 100vh;
            font-family: Arial, sans-serif;
            display: flex;
        }
        /* Sidebar occupe une largeur fixe */
        .sidebar {
            width: 250px;
            flex-shrink: 0;
        }
        /* Contenu principal occupe le reste */
        .main-content {
            flex-grow: 1;
            padding: 2rem;
            display: flex;
            justify-content: center;
            align-items: flex-start;
        }
        .card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 800px;
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
<!-- Sidebar -->
<div class="sidebar">
    <jsp:include page="sidebar.jsp" />
</div>

<!-- Contenu principal -->
<div class="main-content">
    <div class="card">
        <h2>Liste des projets</h2>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>ID</th>
                <th>Nom</th>
                <th>Avancement</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="proj" items="${projets}">
                <tr>
                    <td>${proj.id}</td>
                    <td>${proj.nom}</td>
                    <td>${proj.avancement}</td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>
