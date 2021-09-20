<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="wrapper">

    <jsp:include page="../common/header.jsp"/>

    <jsp:include page="../common/user_sidebar.jsp"/>

    <div class="content-wrapper">
        <section class="content-header">
            <h1>
                General Form Elements
            </h1>
        </section>
        <section class="content">
            <form action="" class="wrap-login100">
                <div class="form-group row">
                    <div class="col-md-2 txt1 text-right"> Login </div>
                    <div class="col-md-6">
                        <div class="wrap-input100">
                            <input class="validate-input input100" type="text" name="login" disabled="disabled" value="${sessionScope.user.login}">
                            <span class="focus-input100"></span>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-2 txt1 text-right"> Mail </div>
                    <div class="col-md-6">
                        <div class="wrap-input100">
                            <input class="validate-input input100" type="text" name="mail" disabled="disabled" value="${sessionScope.user.mail}">
                            <span class="focus-input100"></span>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="txt1 col-md-2 text-right">Name</div>
                    <div class="col-md-6">
                        <div class="wrap-input100">
                            <input class="validate-input input100" type="text" name="name" value="${sessionScope.user.name}">
                            <span class="focus-input100"></span>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-2 txt1 text-right"> Surname </div>
                    <div class="col-md-6">
                        <div class="wrap-input100">
                            <input class="validate-input input100" type="text" name="surname" value="${sessionScope.user.surname}">
                            <span class="focus-input100"></span>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-2 txt1 text-right"> Phone </div>
                    <div class="col-md-6">
                        <div class="wrap-input100">
                            <input class="validate-input input100" type="text" name="phone" value="${sessionScope.user.phone}">
                            <span class="focus-input100"></span>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <div class="col-md-2 txt1 text-right"> Language </div>
                    <div class="col-md-6">
                        <div class="wrap-input100">
                            <select class="validate-input input100" type="text" name="language">
                                <option value="en" selected="selected">English</option>
                                <option value="ru">Russian</option>
                            </select>
                        </div>
                    </div>
                </div>

                <div class="col-md-offset-2 container-login100-form-btn ">
                    <input type="submit" value="Update" class="login100-form-btn" onclick="location.href='${pageContext.request.contextPath}/jsp/guest/main.jsp'"/>
                </div>
            </form>
        </section>
    </div>

    <jsp:include page="../common/footer.jsp"/>
</div>

<script src="${pageContext.request.contextPath}/lib/bootstrap/dist/js/bootstrap.js"></script>
<script src="${pageContext.request.contextPath}/js/adminlte.js"></script>
</body>
</html>

