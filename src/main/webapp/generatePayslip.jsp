<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Générer une fiche de paie</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; margin:0; min-height:100vh; }
        h2 { text-align:center; color:#fff; }
        form { background:#fff; border-radius:12px; padding:2rem; max-width:500px; margin:auto; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; box-sizing:border-box; }
        .note { font-size:.9rem; color:#555; margin-top:.5rem; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
        .actions { max-width:500px; margin:1rem auto; display:flex; gap:1rem; }
        .link { color:#fff; border:1px solid #fff; padding:.5rem 1rem; border-radius:8px; text-decoration:none; }
    </style>
</head>
<body>
<h2>Générer une fiche de paie</h2>
<form action="PayslipCreateServlet" method="post" id="payslipForm">
    <input type="hidden" name="employee_id" id="employee_id" value="<%= request.getAttribute("employee_id") %>">

    <label>Date</label>
    <input type="month" name="period_date" min="${dateArriveEmployee}" max="2100-00" required>

    <div class="note">Le salaire sera récupéré de la table salaire (plus récent antérieur à la date). Les primes et déductions seront calculées à partir de la table salaire_extra pour ce mois et cette année.</div>

    <button class="btn" type="submit">Générer la fiche de paie</button>
</form>

<div class="actions">
    <a class="link" href="PayslipListServlet">Voir les fiches de paie</a>
    <a class="link" href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
