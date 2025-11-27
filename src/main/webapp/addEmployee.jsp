<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajouter un employé</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h2 { text-align:center; color:#fff; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:500px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; }
        select { width:100%; padding:.55rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; background:#fff; }
        .email-preview { margin-top:.5rem; font-size:.95rem; color:#334155; background:#f8fafc; padding:.5rem .75rem; border-radius:8px; border:1px solid #e6eef8; }
        .readonly { background:#f3f4f7; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h2>Ajouter un employé</h2>
<form action="EmployeeCreateServlet" method="post">
    <label>Prénom</label>
    <input type="text" name="first_name" id="first_name" required>

    <label>Nom</label>
    <input type="text" name="last_name" id="last_name" required>

                <label>Email (auto-généré)</label>
                <input type="hidden" name="email" id="email">
                <div class="email-preview" id="email_preview"><span id="preview_text">-</span></div>
    <label>Grade</label>
    <input type="text" name="grade">

    <label>Poste</label>
    <input type="text" name="position_title">

    <label>Salaire de base</label>
    <input type="number" step="0.01" name="base_salary" required>

    <label>ID Département</label>
    <%@ page import="com.dao.DepartmentDAO" %>
    <%@ page import="com.util.DBConnection" %>
    <%@ page import="com.model.Department" %>
    <%@ page import="java.util.List" %>
    <%
        List<Department> depts = new java.util.ArrayList<>();
        try (java.sql.Connection conn = DBConnection.getConnection()) {
            DepartmentDAO ddao = new DepartmentDAO(conn);
            depts = ddao.findAll();
        } catch (Exception ignore) { }
    %>
    <select name="department_id">
        <option value="">-- Aucun --</option>
        <% for (Department d : depts) { %>
            <option value="<%= d.getId() %>"><%= d.getName() %></option>
        <% } %>
    </select>

    <button class="btn" type="submit">Créer l'employé</button>
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
            const local = first + "." + last;
            const domain = "@entreprise.com";
            // email input (without id) and preview showing the final form with ID placeholder
            document.getElementById("email").value = local + domain;
            document.getElementById("preview_text").innerText = local + "<id>" + domain;
        } else {
            document.getElementById("email").value = "";
            document.getElementById("preview_text").innerText = "-";
        }
    }

    document.getElementById("first_name").addEventListener("input", generateEmail);
    document.getElementById("last_name").addEventListener("input", generateEmail);
</script>
</body>
</html>
