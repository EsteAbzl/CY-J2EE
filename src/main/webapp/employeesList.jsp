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
            position: fixed;
            left: 0;
            top: 0;
            overflow-y: auto;
        }
        .main-content {
            flex-grow: 1;
            padding: 2rem;
            padding-left: 4rem;
            margin-left: 250px;
        }
        .card {
            background: #fff;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(0,0,0,0.25);
            padding: 2rem;
            width: 100%;
            max-width: 1200px;
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
        .filter-section {
            background-color: #f8f9fa;
            padding: 1.5rem;
            border-radius: 8px;
            margin-bottom: 2rem;
            border: 1px solid #e0e0e0;
        }
        .filter-section h5 {
            color: #2a5298;
            margin-bottom: 1rem;
            font-weight: 600;
        }
        .filter-row {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
            gap: 1rem;
            margin-bottom: 1rem;
        }
        .filter-row .form-group {
            margin-bottom: 0;
        }
        .filter-controls {
            display: flex;
            gap: 0.5rem;
            justify-content: center;
        }
        .btn-group-custom {
            display: flex;
            gap: 0.5rem;
            justify-content: center;
            margin-top: 1rem;
        }
        .search-input {
            position: relative;
        }
        .search-input input {
            padding-left: 2.5rem;
        }
        .search-icon {
            position: absolute;
            left: 0.75rem;
            top: 50%;
            transform: translateY(-50%);
            color: #999;
        }
        .result-info {
            text-align: center;
            color: #666;
            margin-bottom: 1rem;
            font-size: 0.95rem;
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

        <!-- Section de recherche et filtrage -->
        <form method="get" action="EmployeesListServlet" class="filter-section">
            <h5>🔍 Recherche et Filtrage</h5>

            <div class="filter-row">
                <!-- Barre de recherche -->
                <div class="form-group">
                    <label for="search" class="form-label">Rechercher par nom, prénom ou ID</label>
                    <div class="search-input">
                        <span class="search-icon">🔎</span>
                        <input type="text" class="form-control" id="search" name="search"
                               placeholder="Ex: Dupont, Jean, 5..." value="${searchParam}">
                    </div>
                </div>

                <!-- Filtre Département -->
                <div class="form-group">
                    <label for="departement" class="form-label">Filtrer par Département</label>
                    <select class="form-control" id="departement" name="departement">
                        <option value="">-- Tous les départements --</option>
                        <c:forEach var="dept" items="${departements}">
                            <option value="${dept.id}" <c:if test="${departementFilter == dept.id}">selected</c:if>>
                                ${dept.nom}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Filtre Grade -->
                <div class="form-group">
                    <label for="grade" class="form-label">Filtrer par Grade</label>
                    <select class="form-control" id="grade" name="grade">
                        <option value="">-- Tous les grades --</option>
                        <c:forEach var="gradeItem" items="${grades}">
                            <option value="${gradeItem}" <c:if test="${gradeFilter == gradeItem}">selected</c:if>>
                                ${gradeItem}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- Filtre Poste -->
                <div class="form-group">
                    <label for="poste" class="form-label">Filtrer par Poste</label>
                    <select class="form-control" id="poste" name="poste">
                        <option value="">-- Tous les postes --</option>
                        <c:forEach var="posteItem" items="${postes}">
                            <option value="${posteItem}" <c:if test="${posteFilter == posteItem}">selected</c:if>>
                                ${posteItem}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Boutons d'action -->
            <div class="btn-group-custom">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-search"></i> Rechercher
                </button>
                <a href="EmployeesListServlet" class="btn btn-secondary">
                    <i class="bi bi-arrow-clockwise"></i> Réinitialiser
                </a>
            </div>
        </form>

        <!-- Nombre de résultats -->
        <div class="result-info">
            <strong>${employes.size()}</strong> employé(s) trouvé(s)
        </div>

        <!-- Tableau des employés -->
        <div class="table-responsive">
            <table class="table table-striped table-hover">
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Nom</th>
                    <th>Prénom</th>
                    <th>Email</th>
                    <th>Poste</th>
                    <th>Grade</th>
                    <th>Département</th>
                    <th>Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:choose>
                    <c:when test="${employes.size() > 0}">
                        <c:forEach var="emp" items="${employes}">
                            <tr>
                                <td>${emp.id}</td>
                                <td>${emp.nom}</td>
                                <td>${emp.prenom}</td>
                                <td>${emp.email}</td>
                                <td>${emp.poste}</td>
                                <td>${emp.grade}</td>
                                <td>
                                    <c:forEach var="dept" items="${departements}">
                                        <c:if test="${dept.id == emp.idDepartement}">
                                            ${dept.nom}
                                        </c:if>
                                    </c:forEach>
                                </td>
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
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td colspan="8" class="text-center text-muted">
                                <em>Aucun employé ne correspond à vos critères de recherche.</em>
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
