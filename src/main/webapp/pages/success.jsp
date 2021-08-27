<%--
  Created by IntelliJ IDEA.
  User: alex2
  Date: 25.08.2021
  Time: 17:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}\controller" method="post" name="logoutForm">
    <input type="hidden" name="command" value="logout">

    <br/>
    <input type="submit" value="Logout">
</form>
</body>
</html>
