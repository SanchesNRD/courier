<%--
  Created by IntelliJ IDEA.
  User: alex2
  Date: 25.08.2021
  Time: 17:36
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="controller" name="loginForm" method="POST">
    <input type="hidden" name="command" value="login"/>
    Login:<br/>
    <input type="text" name="login" value=""/>
    <br/>Password: <br/>
    <input type="text" name="password" value=""/>
    <input type="submit" value="Login"/>

</form>
</body>
</html>
