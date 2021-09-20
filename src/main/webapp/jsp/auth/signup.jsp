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
            <form action="${pageContext.request.contextPath}\controller" name="signUpForm" method="POST" class="login100-form validate-form">--%>
                <input type="hidden" name="command" value="login"/>

                <div class="login100-form-title">
                    Account Sign up
                </div>
                <div class="txt1">
                    Name
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="text" name="login" >
                    <span class="focus-input100"></span>
                </div>

                <div class="txt1">
                    Surname
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="text" name="login" >
                </div>

                <div class="txt1">
                    Login
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="text" name="login" >
                </div>

                <div class="txt1"> Password </div>
                <div class="wrap-input100 validate-input" data-validate = "Password is required">
						<span class="btn-show-pass">
                            <i class="fa fa-eye"></i>
						</span>
                    <input class="input100" type="password" name="password" >
                </div>

                <div class="txt1">
                    Mail
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="email" name="login" >
                </div>

                <div class="txt1">
                    Phone
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="tel" name="login" >
                </div>

                <div class="forgot-pass">
                    <a href='${pageContext.request.contextPath}/jsp/auth/login.jsp' class="txt3">
                        Back to login
                    </a>
                </div>

                <div class="container-login100-form-btn">
                    <input type="submit" value="Sign up" class="login100-form-btn"/>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
