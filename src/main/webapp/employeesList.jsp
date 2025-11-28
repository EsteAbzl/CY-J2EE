<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.model.Employee" %>
<%@ page import="com.model.Department" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des employés</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        h1 { text-align:center; color:#fff; }
        h3 { text-align:center; color:#fff; }
        .search-container {
            margin: 30px auto;
            background: linear-gradient(135deg, #ffffff 0%, #f8f9fa 100%);
            padding: 30px;
            border-radius: 12px;
            max-width: 950px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.12), 0 2px 4px rgba(0,0,0,0.08);
            border: 1px solid #e8ebed;
        }
        .search-container h2 {
            margin: 0 0 25px 0;
            font-size: 22px;
            color: #2c3e50;
            text-align: center;
        }
        .search-bar {
            display: flex;
            justify-content: center;
            gap: 12px;
            flex-wrap: wrap;
            align-items: center;
        }
        .search-bar input, .search-bar select {
            padding: 0.75rem 0.9rem;
            border: 2px solid #e1e5e8;
            border-radius: 6px;
            font-size: 14px;
            background: #fff;
            transition: all 0.3s ease;
        }
        .search-bar input:focus, .search-bar select:focus {
            outline: none;
            border-color: #27ae60;
            box-shadow: 0 0 0 3px rgba(39, 174, 96, 0.1);
        }
        .search-bar input[type="text"] {
            min-width: 200px;
            flex: 1;
            min-width: 150px;
        }
        .search-bar select {
            width: 140px;
        }
        .search-label {
            font-weight: 600;
            color: #2c3e50;
            white-space: nowrap;
            font-size: 14px;
        }
        .search-bar button, .search-bar a {
            padding: 0.75rem 1.5rem;
            border: none;
            background: linear-gradient(135deg, #27ae60 0%, #229954 100%);
            color: white;
            border-radius: 6px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            box-shadow: 0 4px 12px rgba(39, 174, 96, 0.3);
            text-decoration: none;
            display: inline-block;
        }
        .search-bar button:hover, .search-bar a:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 16px rgba(39, 174, 96, 0.4);
            background: linear-gradient(135deg, #229954 0%, #1e8449 100%);
        }
        .search-bar a {
            background: linear-gradient(135deg, #95a5a6 0%, #7f8c8d 100%);
            box-shadow: 0 4px 12px rgba(149, 165, 166, 0.3);
            margin-left: 8px;
        }
        .search-bar a:hover {
            background: linear-gradient(135deg, #7f8c8d 0%, #697174 100%);
            box-shadow: 0 6px 16px rgba(149, 165, 166, 0.4);
        }
        table { width:90%; border-collapse:collapse; margin:20px auto; background:#fff; box-shadow:0 8px 20px rgba(0,0,0,0.25); border-radius:8px; overflow:hidden; }
        th, td { border:1px solid #ddd; padding:10px 15px; text-align:left; }
        th { background:#34495e; color:#fff; }
        tr:nth-child(even) { background:#f2f2f2; }
        .btn { background:#27ae60; color:#fff; border:none; padding:8px 16px; border-radius:4px; font-weight:bold; cursor:pointer; text-decoration: none; display:inline-block; }
        .btn:hover { background:#229954; }
        .btn-danger { background:#c0392b; }
        .btn-danger:hover { background:#962d22; }
        .btn-extra { background:#16a085; }
        .btn-extra:hover { background:#138d75; }
        .actions { text-align:center; margin-top:1rem; }
    </style>
</head>
<body>
<h1>Liste des employés</h1>

<div class="search-container">
    <h2>Rechercher et filtrer</h2>
    <form action="EmployeeListServlet" method="get" class="search-bar">
        <input type="text" name="query" placeholder="Nom, prénom ou ID employé" value="${searchQuery != null ? searchQuery : ''}">
        
        <span class="search-label">Grade :</span>
        <select name="grade">
            <option value="">--</option>
            <c:forEach var="g" items="${grades}">
                <option value="${g}" <c:if test="${grade == g}">selected</c:if>>${g}</option>
            </c:forEach>
        </select>

        <span class="search-label">Poste :</span>
        <select name="position">
            <option value="">--</option>
            <c:forEach var="p" items="${positions}">
                <option value="${p}" <c:if test="${position == p}">selected</c:if>>${p}</option>
            </c:forEach>
        </select>

        <span class="search-label">Département :</span>
        <select name="department">
            <option value="">--</option>
            <c:forEach var="d" items="${departments}">
                <option value="${d.id}" <c:if test="${department == d.id}">selected</c:if>>${d.name}</option>
            </c:forEach>
        </select>

        <button type="submit">🔍 Rechercher</button>
        <a href="EmployeeListServlet">↺ Réinitialiser</a>
    </form>
</div>

<table>
    <tr>
        <th>ID</th>
        <th>Nom</th>
        <th>Email</th>
        <th>Grade</th>
        <th>Poste</th>
        <th>Département</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="e" items="${employees}">
    <tr>
        <td>${e.id}</td>
        <td>${e.lastName} ${e.firstName}</td>
        <td>${e.email}</td>
        <td>${e.grade}</td>
        <td>${e.positionTitle}</td>
        <td>${e.departmentId}</td>
        <td>
            <a class="btn" href="EmployeeEditServlet?id=${e.id}">✏️ Modifier</a>
            <a class="btn btn-extra" href="updateSalary.jsp?id=${e.id}">💰 Ajout extra</a>
            <a class="btn btn-danger" href="EmployeeDeleteServlet?id=${e.id}" onclick="return confirm('Supprimer cet employé ?');">🗑️ Supprimer</a>
        </td>
    </tr>
    </c:forEach>
</table>
<c:if test="${employees.size() == 0}">
    <h3> Aucun employé ne correspond à ces critères </h3>
</c:if>

<div class="actions">
    <a class="btn" href="addEmployee.jsp">➕ Ajouter un employé</a>
    <a class="btn" href="dashboard.jsp">🏠 Retour au tableau de bord</a>
</div>
</body>
</html>
