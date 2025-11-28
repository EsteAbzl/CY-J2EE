<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Supprimer un employé</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; min-height:100vh; margin:0; }
        h2 { text-align:center; color:#fff; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:400px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; box-sizing:border-box; }
        .actions { display:flex; gap:1rem; margin-top:1.5rem; }
        .btn { background:#c0392b; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; cursor:pointer; flex:1; text-decoration:none; text-align:center; transition:.3s; }
        .btn:hover { background:#962d22; }
        .btn-secondary { background:#2A5298FF; color:#fff; }
        .btn-secondary:hover { background:#1E3C72FF; }
    </style>
</head>
<body>
<h2>Supprimer un employé</h2>
<form>
    <label>ID Employé</label>
    <input type="number" id="employeeId" name="id" required>
    <div class="actions">
        <button class="btn" type="button" onclick="deleteEmployee()">Supprimer</button>
        <a class="btn btn-secondary" href="EmployeeListServlet">Retour</a>
    </div>
</form>

<script>
    function deleteEmployee() {
        const employeeId = document.getElementById('employeeId').value;
        if (employeeId && confirm('Êtes-vous sûr de vouloir supprimer cet employé ?')) {
            window.location.href = 'EmployeeDeleteServlet?id=' + employeeId;
        }
    }
</script>
</body>
</html>
