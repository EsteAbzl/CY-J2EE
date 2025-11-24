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
        .readonly { background:#f3f4f7; }
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
    <label>ID Employé</label>
    <input type="number" name="employee_id" id="employee_id" required>

    <label>Année</label>
    <input type="number" name="period_year" id="period_year" required min="2000" max="2100">

    <label>Mois</label>
    <input type="number" name="period_month" id="period_month" min="1" max="12" required>

    <label>Salaire de base</label>
    <input type="number" step="0.01" name="base_salary" id="base_salary" required>

    <label>Primes</label>
    <input type="number" step="0.01" name="bonuses" id="bonuses" value="0">

    <label>Déductions</label>
    <input type="number" step="0.01" name="deductions" id="deductions" value="0">

    <label>Net à payer (calculé)</label>
    <input type="number" step="0.01" name="net_pay" id="net_pay" class="readonly" readonly>

    <div class="note">Net = Salaire de base + Primes − Déductions. Le champ est calculé automatiquement.</div>

    <button class="btn" type="submit">Générer la fiche de paie</button>
</form>

<div class="actions">
    <a class="link" href="PayslipListServlet">Voir les fiches de paie</a>
    <a class="link" href="dashboard.jsp">Retour au tableau de bord</a>
</div>

<script>
    function num(v){ const n = parseFloat(v); return isNaN(n) ? 0 : n; }
    function updateNet(){
        const base = num(document.getElementById('base_salary').value);
        const bonuses = num(document.getElementById('bonuses').value);
        const deductions = num(document.getElementById('deductions').value);
        const net = base + bonuses - deductions;
        document.getElementById('net_pay').value = net.toFixed(2);
    }
    ['base_salary','bonuses','deductions'].forEach(id => {
        const el = document.getElementById(id);
        if (el) el.addEventListener('input', updateNet);
    });
    window.addEventListener('DOMContentLoaded', updateNet);
</script>
</body>
</html>
