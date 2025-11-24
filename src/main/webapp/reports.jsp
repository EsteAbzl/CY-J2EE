<%@ page import="java.util.Map" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<jsp:include page="header.jsp"/>
<h2>Rapports RH</h2>

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

<jsp:include page="footer.jsp"/>
