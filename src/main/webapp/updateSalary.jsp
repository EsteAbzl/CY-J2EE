<%@ page contentType="text/html; charset=UTF-8" %>
<%
    String employeeId = request.getParameter("id");
    if (employeeId == null || employeeId.isEmpty()) {
        response.sendRedirect("EmployeeListServlet?error=missingId");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Ajout d'extra</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        h2 { text-align:center; color:#fff; margin-bottom:1rem; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:500px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input, textarea { width:100%; padding:.5rem; border-radius:6px; border:1px solid #ccc; margin-top:.25rem; box-sizing:border-box; }
        .btn { margin-top:1.5rem; background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; width:100%; cursor:pointer; transition:.3s; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<h2>Ajout d'extraa</h2>
<form action="UpdateSalaryServlet" method="post">
    <input type="hidden" name="employee_id" value="<%= employeeId %>">

    <label for="date">Date *</label>
    <input type="date" name="date" id="date" value="00/00/2025" min="00-00-2000" max="00-00-2100" required/>

    <label for="extra">Montant extra (prime ou déduction) *</label>
    <input type="number" step="0.01" name="extra" id="extra" max="99999999" min="-99999999" value="0" required>

    <label for="description">Description (facultatif)</label>
    <textarea name="description" id="description" rows="2" maxlength="249"></textarea>

    <div style="display:flex; gap:1rem; margin-top:1.5rem;">
        <a class="btn" href="EmployeeListServlet" style="background:#888; text-align:center; text-decoration:none; flex:1;">Retour</a>
        <button class="btn" type="submit" style="flex:1;">Ajouter</button>
    </div>
</form>
</body>
</html>
