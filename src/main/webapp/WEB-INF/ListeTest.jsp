<%@ page import="java.util.List" %>
<%@ page import="com.model.Test" %>
<%
    List<Test> tests = (List<Test>) request.getAttribute("test_Liste");
%>
<html><body>
<h2>Liste des tests</h2>
<a href="${pageContext.request.contextPath}/AjoutTest.jsp">Ajouter</a>
<table border="1">
    <tr><th>ID</th><th>Thing</th><th>Action</th></tr>
    <% for (Test e : tests) { %>
    <tr>
        <td><%= e.getId() %></td>
        <td><%= e.getThings() %></td>
        <td>
            <form method="post" action="${pageContext.request.contextPath}/test" style="display:inline">
                <input type="hidden" name="action" value="delete">
                <input type="hidden" name="id" value="<%= e.getId() %>">
                <input type="submit" value="Supprimer">
            </form>
        </td>
    </tr>
    <% } %>
</table>
</body></html>
