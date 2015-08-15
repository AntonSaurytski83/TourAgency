<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://epam.com/taglib/custom" %>

<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="main.page.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<div class="content">
    <h1><fmt:message key="main.page.intro.title"/></h1>
    <p><fmt:message key="main.page.intro.body"/></p>
    <h1><fmt:message key="main.page.how_to"/></h1>
    <div class="blocks">
        <div class="block block-search">
            <h1><fmt:message key="block.search.title"/></h1>
            <img src="${pageContext.servletContext.contextPath}/images/search.png"/>
            <p><fmt:message key="block.search.text"/></p>
        </div>
        <div class="block block-order">
            <h1><fmt:message key="block.order.title"/></h1>
            <img src="${pageContext.servletContext.contextPath}/images/order.png"/>
            <p><fmt:message key="block.order.text"/></p>
        </div>
        <div class="block block-complete">
            <h1><fmt:message key="block.complete.title"/></h1>
            <img src="${pageContext.servletContext.contextPath}/images/accept.png"/>
            <p><fmt:message key="block.complete.text"/></p>
        </div>
    </div>
</div>
<%@include file="../WEB-INF/jspf/footer.jspf" %>
</body>
</html>