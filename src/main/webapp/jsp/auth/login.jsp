<%@ page contentType="text/html; charset=UTF-8" language="java" %>
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
                <div class="login100-form-title">
						Account Login
                </div>
                <div class="txt1">
						Login
                </div>
                <div class="wrap-input100 validate-input" data-validate = "Username is required">
                    <input class="input100" type="text" name="login" >
                    <span class="focus-input100"></span>
                </div>

                <div class="txt1"> Password </div>
                <div class="wrap-input100 validate-input" data-validate = "Password is required">
						<span class="btn-show-pass">
                            <i class="fa fa-eye"></i>
						</span>
                    <input class="input100" type="password" name="password" >
                    <span class="focus-input100"></span>
                </div>

                <div class="forgot-pass">
                        <a href="${pageContext.request.contextPath}/controller?command=go_to_forgot_pass" class="txt3">
                            Forgot Password?
                        </a>
                </div>

                <div class="container-login100-form-btn">
                    <input type="submit" name="command" value="Login" class="login100-form-btn"/>
                    <input type="hidden" name="command" value="go_to_sign_up">
                    <input type="submit" value="Sign Up" class="signup-form-btn"/>
                </div>

            </form>
        </div>
    </div>
</div>
</body>
</html>
