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
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; }
        .btn { background:#c0392b; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%;}
        a { background:#2A5298FF; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; text-decoration: none; transition:.3s;                                                                                                                                                  }




        .btn:hover{ background:#962d22; }
        a:hover {background:#1E3C72FF;}
    </style>
</head>
<body>
<h2>Supprimer un employé</h2>
<form action="EmployeeDeleteServlet" method="get">
    <label>ID Employé</label>
    <input type="number" name="id" required>
    <button class="btn" type="submit">Supprimer</button>
</form>
<div class="actions">
    <a class="link" href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
