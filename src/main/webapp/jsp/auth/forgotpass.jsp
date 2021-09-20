<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/util.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth/main.css">
</head>
<body>
<div class="limiter">
    <div class="container-login100">
        <div class="wrap-login100">
            <form action="${pageContext.request.contextPath}\controller" name="loginForm" method="POST" class="login100-form validate-form">
                <input type="hidden" name="command" value="login"/>

                <div class="login100-form-title">
                    Forgot password
                </div>
                <div class="txt1">
                    Mail
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="email" name="mail" >
                    <span class="focus-input100"></span>
                </div>

                <div class="container-login100-form-btn">
                    <input type="submit" value="Sent" class="signup-form-btn" onclick="location.href='jsp/login.jsp'"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
