<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.model.Employee" %>
<%
    Employee e = (Employee) session.getAttribute("SESSION_employee");
    if (e == null) {
        response.sendRedirect(request.getContextPath() + "/Login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Changer le mot de passe</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg,#1e3c72,#2a5298); /* fond harmonisé */
            margin: 0;
            padding: 0;
            min-height: 100vh;
            display:flex;
            justify-content:center;
            align-items:center;

        }
        .card {
            background:#fff;
            padding:30px;
            width:100%;
            max-width:420px;
            border-radius:8px;
            box-shadow:0 8px 20px rgba(0,0,0,0.25);
        }
        h3 {
            text-align:center;
            color:#2c3e50;
            margin-bottom:20px;
        }
        label {
            font-weight:bold;
            color:#34495e;
        }
        .form-control {
            border-radius:4px;
            border:1px solid #ccc;
            padding:8px;
        }
        .btn-submit {
            background:#27ae60;
            color:#fff;
            border:none;
            padding:10px 20px;
            border-radius:4px;
            font-weight:bold;
            cursor:pointer;
            transition: background 0.3s ease;
            width:100%;
        }
        .btn-submit:hover {
            background:#219150;
        }
        .error { background:#e74c3c; color:#fff; padding:.75rem; border-radius:8px; margin-bottom:1rem; text-align:center; }
    </style>
</head>
<body>
<% String error = (String) request.getAttribute("errorMessage");
    if (error != null) { %>
<div class="error"><%= error %></div>
<br>
<% } %>
<div class="card">
    <h3>Bonjour <%= e.getLastName() != null ? e.getFirstName() : e.getEmail() %>, veuillez changer votre mot de passe</h3>
    <form action="${pageContext.request.contextPath}/changePassword" method="post">
        <div class="mb-3">
            <label for="newMdp" class="form-label">Nouveau mot de passe</label>
            <input type="password" name="newMdp" id="newMdp" class="form-control" required />
            <br><br>
        </div>
        <div class="mb-3">
            <label for="confirmMdp" class="form-label">Confirmer le mot de passe</label>
            <input type="password" name="confirmMdp" id="confirmMdp" class="form-control" required />
            <br><br>
        </div>
        <button type="submit" class="btn-submit">Enregistrer</button>
    </form>
</div>
</body>
</html>
