<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.model.Employee" %>
<%@ page import="com.model.Department" %>
<%@ page import="java.util.List" %>
<%
    Employee emp = (Employee) request.getAttribute("employee");
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Modifier un employé</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h2 { text-align:center; color:#fff; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:500px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; }
        .readonly { background:#f3f4f7; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h2>Modifier un employé</h2>
<form action="EmployeeEditServlet" method="post">
    <input type="hidden" name="id" value="<%= emp.getId() %>">

    <label>Prénom</label>
    <input type="text" name="first_name" id="first_name" value="<%= emp.getFirstName() %>" required>

    <label>Nom</label>
    <input type="text" name="last_name" id="last_name" value="<%= emp.getLastName() %>" required>

    <label>Email</label>
    <input type="email" name="email" id="email" class="readonly" value="<%= emp.getEmail() %>" readonly>

    <label>Grade</label>
    <input type="text" name="grade" value="<%= emp.getGrade() %>">

    <label>Poste</label>
    <input type="text" name="position_title" value="<%= emp.getPositionTitle() %>">

    <label>Salaire de base</label>
    <input type="number" step="0.01" name="base_salary" value="<%= emp.getBaseSalary() %>" required>

    <label>Département</label>
    <select name="department_id" required>
        <option value="">-- Sélectionnez un département --</option>
        <%
            List<Department> departments = (List<Department>) request.getAttribute("departments");
            if (departments != null) {
                for (com.model.Department d : departments) {
                    String selected = (emp.getDepartmentId() != null && emp.getDepartmentId() == d.getId()) ? "selected" : "";
        %>

        <option value="<%= d.getId() %>" <%= selected %>><%= d.getName() %></option>
        <%
                }
            }
        %>
    </select>

    <button class="btn" type="submit">Mettre à jour</button>
</form>

<script>
    // Fonction pour supprimer accents et caractères spéciaux
    function normalize(str) {
        return str
            .normalize("NFD")                // décompose les accents
            .replace(/[\u0300-\u036f]/g, "") // supprime les diacritiques
            .replace(/[^a-zA-Z]/g, "")       // garde uniquement lettres
            .toLowerCase();
    }

    function generateEmail() {
        const first = normalize(document.getElementById("first_name").value.trim());
        const last = normalize(document.getElementById("last_name").value.trim());
        if (first && last) {
            document.getElementById("email").value = first + "." + last + "@entreprise.com";
        } else {
            document.getElementById("email").value = "";
        }
    }

    document.getElementById("first_name").addEventListener("input", generateEmail);
    document.getElementById("last_name").addEventListener("input", generateEmail);
</script>
</body>
</html>
