<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://epam.com/taglib/custom" %>


<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="auth.page.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<div class="content">
    <h1><fmt:message key="auth.page.title"/></h1>
    <p><fmt:message key="auth.page.message"/></p>
    <form method="post" id="loginForm">
        <input type="hidden" name="command" value="login"/>
        <label for="login"><fmt:message key="auth.page.login_form.login"/> :</label>
        <input id="login" type="text" name="login" autocomplete="off" />
        <label for="password"><fmt:message key="auth.page.login_form.password"/>:</label>
        <input id="password" type="password" name="password" autocomplete="off"/>
        <input type="submit" value="<fmt:message key="auth.page.login_form.submit"/>"/>
    </form>
</div>
<%@include file="../WEB-INF/jspf/footer.jspf" %>
</body>
</html>