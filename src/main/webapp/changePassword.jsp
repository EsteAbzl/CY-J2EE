<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.model.Employee" %>
<%
    Employee e = (Employee) session.getAttribute("employe");
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
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { display:flex; justify-content:center; align-items:center; min-height:100vh; background:#f4f6f8; }
        .card { padding:20px; width:100%; max-width:420px; }
    </style>
</head>
<body>
<div class="card">
    <h3>Bonjour <%= e.getLastName() != null ? e.getFirstName() : e.getEmail() %>, veuillez changer votre mot de passe</h3>
    <form action="${pageContext.request.contextPath}/changePassword" method="post">
        <div class="mb-3">
            <label for="newMdp" class="form-label">Nouveau mot de passe</label>
            <input type="password" name="newMdp" id="newMdp" class="form-control" required />
        </div>
        <div class="mb-3">
            <label for="confirmMdp" class="form-label">Confirmer le mot de passe</label>
            <input type="password" name="confirmMdp" id="confirmMdp" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary">Enregistrer</button>
    </form>
</div>
</body>
</html>
