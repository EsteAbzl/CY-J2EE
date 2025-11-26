<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.model.SalaireExtra" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Gestion des Salaires Extras</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; padding:2rem; margin:0; min-height:100vh; }
        h2 { text-align:center; color:#fff; }
        .container { max-width:900px; margin:0 auto; }
        .form-section { background:#fff; border-radius:12px; padding:2rem; margin-bottom:2rem; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        label { display:block; margin-top:1rem; font-weight:bold; color:#2a5298; }
        input, textarea { width:100%; padding:.5rem; border:1px solid #ccc; border-radius:8px; margin-top:.25rem; box-sizing:border-box; }
        .btn { background:#2a5298; color:#fff; border:none; padding:.75rem; border-radius:8px; font-weight:bold; margin-top:1rem; cursor:pointer; width:100%; }
        .btn:hover { background:#1e3c72; }
        table { width:100%; border-collapse:collapse; background:#fff; border-radius:12px; overflow:hidden; box-shadow:0 8px 20px rgba(0,0,0,0.25); }
        th, td { padding:1rem; text-align:left; border-bottom:1px solid #ddd; }
        th { background:#2a5298; color:#fff; }
        tr:hover { background:#f3f4f7; }
        .actions { max-width:900px; margin:2rem auto; display:flex; gap:1rem; }
        .link { color:#fff; border:1px solid #fff; padding:.5rem 1rem; border-radius:8px; text-decoration:none; display:inline-block; }
        .link:hover { background:rgba(255,255,255,0.1); }
    </style>
</head>
<body>
<div class="container">
    <h2>Gestion des Salaires Extras (Primes et Déductions)</h2>

    <div class="form-section">
        <h3>Ajouter un nouveau salaire extra</h3>
        <form action="SalaireExtraServlet" method="post">
            <label>ID Employé</label>
            <input type="number" name="employee_id" required>

            <label>Montant (positif pour prime, négatif pour déduction)</label>
            <input type="number" step="0.01" name="montant" required>

            <label>Motif</label>
            <textarea name="motif" rows="3"></textarea>

            <label>Date</label>
            <input type="date" name="date" required>

            <button class="btn" type="submit">Ajouter le salaire extra</button>
        </form>
    </div>

    <div class="form-section">
        <h3>Liste des salaires extras</h3>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>ID Employé</th>
                    <th>Montant</th>
                    <th>Motif</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <%
                    List<SalaireExtra> salairesExtra = (List<SalaireExtra>) request.getAttribute("salairesExtra");
                    if (salairesExtra != null) {
                        for (SalaireExtra se : salairesExtra) {
                %>
                <tr>
                    <td><%= se.getId() %></td>
                    <td><%= se.getEmployeeId() %></td>
                    <td><%= se.getMontant() %></td>
                    <td><%= se.getMotif() %></td>
                    <td><%= se.getDate() %></td>
                </tr>
                <%
                        }
                    }
                %>
            </tbody>
        </table>
    </div>
</div>

<div class="actions">
    <a class="link" href="dashboard.jsp">Retour au tableau de bord</a>
</div>
</body>
</html>
