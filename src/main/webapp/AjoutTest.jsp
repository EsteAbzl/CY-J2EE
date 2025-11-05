<%@ page contentType="text/html;charset=UTF-8" %>
<html><body>
<h1>Ex 1</h1>
<form action="${pageContext.request.contextPath}/test" method="post">
    <input type="hidden" name="action" value="add">
    <table>
        <tr>
            <td>Thing:</td><td><input type="text" name="things"></td>
        </tr>
    </table>
    <input type="submit" value="Ajouter">
</form>
<a href="${pageContext.request.contextPath}/test">Liste</a>
</body></html>
