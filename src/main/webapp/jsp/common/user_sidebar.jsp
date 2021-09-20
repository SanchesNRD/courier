<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<aside class="main-sidebar">
    <section class="sidebar">
        <ul class="sidebar-menu" data-widget="tree">
            <li><a href="${pageContext.request.contextPath}/controller?command=go_to_user_profile"> Profile</a></li>
            <li><a href="pages/UI/icons.html"> Icons</a></li>
            <li><a href="pages/UI/buttons.html"> Buttons</a></li>
            <li><a href="pages/UI/sliders.html"> Sliders</a></li>
            <li><a href="pages/UI/timeline.html"> Timeline</a></li>
            <li><a href="${pageContext.request.contextPath}/controller?command=logout"> Logout</a></li>
        </ul>
    </section>
</aside>
</html>
