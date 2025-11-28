<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accès refusé</title>
    <style>
        body { background:linear-gradient(135deg,#1e3c72,#2a5298); font-family:Arial; margin:0; padding:2rem; min-height:100vh; }
        .container { max-width:600px; margin:5rem auto; background:#fff; border-radius:12px; padding:2rem; box-shadow:0 8px 20px rgba(0,0,0,0.25); text-align:center; }
        h1 { color:#c0392b; margin-bottom:1rem; }
        p { color:#555; font-size:1.1rem; margin-bottom:2rem; }
        .btn { display:inline-block; background:#2a5298; color:#fff; text-decoration:none; padding:0.75rem 2rem; border-radius:8px; font-weight:bold; transition:0.3s; }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<div class="container">
    <h1>🔒 Accès refusé</h1>
    <p>${message != null ? message : 'Vous n\'avez pas les droits d\'accès pour cette ressource.'}</p>
    <a href="javascript:history.back();" class="btn">← Retour</a>
    <a href="dashboard.jsp" class="btn">🏠 Tableau de bord</a>
</div>
</body>
</html>
