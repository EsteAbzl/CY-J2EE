<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Rapports</title>
    <style>
        body { font-family: Arial, sans-serif; background:linear-gradient(135deg,#1e3c72,#2a5298); margin:0; padding:0; min-height:100vh; }
        header { background:#2c3e50; color:#fff; padding:15px; text-align:center; }
        h2, h3 { text-align:center; color:#fff; margin:20px 0; }
        table { border-collapse:collapse; width:90%; margin:20px auto; background:#fff; box-shadow:0 8px 20px rgba(0,0,0,0.25); border-radius:8px; overflow:hidden; }
        th, td { border:1px solid #ddd; padding:10px 15px; text-align:left; }
        th { background:#34495e; color:#fff; }
        tr:nth-child(even) { background:#f2f2f2; }
        canvas { display:block; margin:30px auto; max-width:600px; border:1px solid #ccc; background:#fff; border-radius:8px; }
        .back { text-align:center; margin:20px; }
        .back a { text-decoration:none; background:#27ae60; color:#fff; padding:8px 16px; border-radius:4px; font-weight:bold; }
    </style>
</head>
<body>
<header>
    <h1>Rapports</h1>
</header>

<h2>Statistiques des employés</h2>

<!-- Employés par département -->
<h3>Employés par département</h3>
<table>
    <tr><th>Département</th><th>Nombre d’employés</th></tr>
    <%
        Map<String,Integer> byDept = (Map<String,Integer>) request.getAttribute("employeesByDepartment");
        if (byDept != null) {
            for (Map.Entry<String,Integer> e : byDept.entrySet()) {
    %>
    <tr><td><%=e.getKey()%></td><td><%=e.getValue()%></td></tr>
    <% } } %>
</table>
<canvas id="deptChart" width="600" height="400"></canvas>

<!-- Employés par projet -->
<h3>Employés par projet</h3>
<table>
    <tr><th>Projet</th><th>Nombre d’employés</th></tr>
    <%
        Map<String,Integer> byProj = (Map<String,Integer>) request.getAttribute("employeesByProject");
        if (byProj != null) {
            for (Map.Entry<String,Integer> e : byProj.entrySet()) {
    %>
    <tr><td><%=e.getKey()%></td><td><%=e.getValue()%></td></tr>
    <% } } %>
</table>
<canvas id="projChart" width="600" height="400"></canvas>

<!-- Employés par grade -->
<h3>Employés par grade</h3>
<table>
    <tr><th>Grade</th><th>Nombre d’employés</th></tr>
    <%
        Map<String,Integer> byGrade = (Map<String,Integer>) request.getAttribute("employeesByGrade");
        if (byGrade != null) {
            for (Map.Entry<String,Integer> e : byGrade.entrySet()) {
    %>
    <tr><td><%=e.getKey()%></td><td><%=e.getValue()%></td></tr>
    <% } } %>
</table>
<canvas id="gradeChart" width="400" height="400"></canvas>

<div class="back">
    <a href="dashboard.jsp">Retour au tableau de bord</a>
</div>

<script>
    // Injection des données JSP vers JS
    const deptData = { <% if (byDept != null) { for (Map.Entry<String,Integer> e : byDept.entrySet()) { %>"<%=e.getKey()%>":<%=e.getValue()%>,<% } } %> };
    const projData = { <% if (byProj != null) { for (Map.Entry<String,Integer> e : byProj.entrySet()) { %>"<%=e.getKey()%>":<%=e.getValue()%>,<% } } %> };
    const gradeData = { <% if (byGrade != null) { for (Map.Entry<String,Integer> e : byGrade.entrySet()) { %>"<%=e.getKey()%>":<%=e.getValue()%>,<% } } %> };

    // Fonction pour dessiner un diagramme à barres
    function drawBarChart(canvasId, data, color, title) {
        const canvas = document.getElementById(canvasId);
        const ctx = canvas.getContext("2d");
        ctx.clearRect(0,0,canvas.width,canvas.height);

        const labels = Object.keys(data);
        const values = Object.values(data);
        const maxValue = Math.max(...values);

        const barWidth = 50;
        const gap = 70;
        const chartHeight = canvas.height - 60;

        ctx.fillStyle = "#34495e";
        ctx.font = "18px Arial";
        const textWidth = ctx.measureText(title).width;
        ctx.fillText(title, (canvas.width - textWidth) / 2, 30);

        labels.forEach((label, i) => {
            const barHeight = (values[i] / maxValue) * chartHeight;
            const x = i * (barWidth + gap) + 60;
            const y = canvas.height - barHeight - 30;

            ctx.fillStyle = color;
            ctx.fillRect(x, y, barWidth, barHeight);

            ctx.fillStyle = "#000";
            ctx.font = "14px Arial";

            // Centrer le label sous la barre
            const labelWidth = ctx.measureText(label).width;
            ctx.fillText(label, x + (barWidth - labelWidth) / 2, canvas.height - 10);

            // Centrer la valeur au-dessus de la barre
            const valueText = values[i].toString();
            const valueWidth = ctx.measureText(valueText).width;
            ctx.fillText(valueText, x + (barWidth - valueWidth) / 2, y - 5);
        });
    }

    // Fonction pour dessiner un camembert
    function drawPieChart(canvasId, data, colors, title) {
        const canvas = document.getElementById(canvasId);
        const ctx = canvas.getContext("2d");
        ctx.clearRect(0,0,canvas.width,canvas.height);

        const total = Object.values(data).reduce((a,b)=>a+b,0);
        let startAngle = 0;
        let i = 0;

        ctx.fillStyle = "#34495e";
        ctx.font = "18px Arial";
        const textWidth = ctx.measureText(title).width;
        ctx.fillText(title, (canvas.width - textWidth) / 2, 30);

        for (const [label, value] of Object.entries(data)) {
            const sliceAngle = (value / total) * 2 * Math.PI;

            ctx.fillStyle = colors[i % colors.length];
            ctx.beginPath();
            ctx.moveTo(200,200);
            ctx.arc(200,200,150,startAngle,startAngle+sliceAngle);
            ctx.closePath();
            ctx.fill();

            // Label
            const midAngle = startAngle + sliceAngle/2;
            const x = 200 + Math.cos(midAngle)*100;
            const y = 200 + Math.sin(midAngle)*100;
            ctx.fillStyle = "#000";
            ctx.font = "14px Arial";
            ctx.fillText(label, x, y);

            startAngle += sliceAngle;
            i++;
        }
    }

    // Dessiner les graphiques
    drawBarChart("deptChart", deptData, "#2980b9", "Employés par département");
    drawBarChart("projChart", projData, "#27ae60", "Employés par projet");
    drawPieChart("gradeChart", gradeData, ["#f39c12","#8e44ad","#c0392b"], "Répartition par grade");
</script>
</body>
</html>
