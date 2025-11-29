<%@ page isErrorPage="true" contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Accès refusé</title>
    <style>
        body {
            background:linear-gradient(135deg,#1e3c72,#2a5298);
            font-family:Arial, sans-serif;
            margin:0;
            padding:2rem;
            min-height:100vh;
            display:flex;
            align-items:center;
            justify-content:center;
        }
        .container {
            background:#fff;
            border-radius:12px;
            box-shadow:0 8px 20px rgba(0,0,0,0.25);
            padding:2rem;
            max-width:600px;
            text-align:center;
        }
        h1 { color:#c0392b; margin-bottom:1rem; }
        p { color:#333; margin-bottom:1rem; }
        pre {
            background:#f6f6f6;
            padding:1rem;
            border-radius:8px;
            text-align:left;
            overflow-x:auto;
            font-size:.9rem;
            color:#555;
        }
        .btn {
            display:inline-block;
            background:#2a5298;
            color:#fff;
            text-decoration:none;
            padding:.75rem 1.5rem;
            border-radius:8px;
            font-weight:bold;
            margin-top:1rem;
            transition:.3s;
        }
        .btn:hover { background:#1e3c72; }
    </style>
</head>
<body>
<div class="container">
    <h1>🛑 Vous n'avez pas les permissions pour accéder à cette page</h1>

</div>
</body>
</html>
