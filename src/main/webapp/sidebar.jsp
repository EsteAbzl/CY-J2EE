<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
    .sidebar {
        position: fixed;
        top: 0;
        left: 0;
        height: 100%;
        width: 240px;
        background: linear-gradient(135deg, #1e3c72, #2a5298);
        color: #fff;
        padding-top: 1rem;
        box-shadow: 2px 0 8px rgba(0,0,0,0.2);
    }
    .sidebar h3 {
        text-align: center;
        margin-bottom: 1rem;
        font-weight: bold;
    }
    .sidebar a {
        color: #fff;
        text-decoration: none;
    }
    .sidebar .nav-link {
        padding: 10px 20px;
        border-radius: 6px;
    }
    .sidebar .nav-link:hover {
        background-color: #1e3c72;
    }
</style>

<div class="sidebar">
    <h3>RH Dashboard</h3>
    <div class="nav flex-column">

        <!-- Employés -->
        <a class="nav-link" data-bs-toggle="collapse" href="#employeesMenu" role="button" aria-expanded="false" aria-controls="employeesMenu">
            👥 Employés
        </a>
        <div class="collapse" id="employeesMenu">
            <div class="ms-3">
                <a class="nav-link" href="addEmployee">➕ Ajouter</a>
                <a class="nav-link" href="DeleteEmployeeServlet">🗑 Supprimer</a>
                <a class="nav-link" href="EmployeesListServlet">📋 Liste des employés</a>
                <a class="nav-link" href="generatePayslip.jsp">💶 Générer fiche de paie</a>
            </div>
        </div>

        <!-- Départements -->
        <a class="nav-link" data-bs-toggle="collapse" href="#departmentsMenu" role="button" aria-expanded="false" aria-controls="departmentsMenu">
            🏢 Départements
        </a>
        <div class="collapse" id="departmentsMenu">
            <div class="ms-3">
                <a class="nav-link" href="addDepartment.jsp">➕ Ajouter</a>
                <a class="nav-link" href="deleteDepartment.jsp">🗑 Supprimer</a>
                <a class="nav-link" href="DepartmentsListServlet">📋 Liste des départements</a>
                <a class="nav-link" href="employeesByDepartment.jsp">👥 Employés par département</a>
            </div>
        </div>

        <!-- Projets -->
        <a class="nav-link" data-bs-toggle="collapse" href="#projectsMenu" role="button" aria-expanded="false" aria-controls="projectsMenu">
            📂 Projets
        </a>
        <div class="collapse" id="projectsMenu">
            <div class="ms-3">
                <a class="nav-link" href="AddProjectServlet">➕ Ajouter</a>
                <a class="nav-link" href="DeleteProjectServlet">🗑 Supprimer</a>
                <a class="nav-link" href="ProjectsListServlet">📋 Liste des projets</a>
                <a class="nav-link" href="employeesByProject.jsp">👥 Employés par projet</a>
            </div>
        </div>

        <hr style="border-color: rgba(255,255,255,0.3);">
        <a class="nav-link" href="dashboardRH.jsp">⬅ Retour Accueil</a>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
