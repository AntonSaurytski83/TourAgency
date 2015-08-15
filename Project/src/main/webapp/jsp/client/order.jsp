<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://epam.com/taglib/custom" %>


<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="order.prepare.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
</head>
<body>
<%@include file="../../WEB-INF/jspf/header.jspf" %>
<div class="content">
    <h1><fmt:message key="order.prepare.hint"/>:</h1>
    <div class="tour-order">
        <h3>${tour.tourname}</h3>
        <p>${tour.details}</p>
        <p class="total"><fmt:message key="order.prepare.total"/>: ${amount} USD</p>
    </div>
    <div class="actions">
        <a class="btn" href="app?c=order&id=${tour.id}&confirm=1&lang=${locale}"><fmt:message
                key="order.prepare.purchase"/></a>
        <a href="app?c=tours&lang=${locale}"><fmt:message key="order.prepare.cancel"/></a>
   </div>
</div>
<%@include file="../../WEB-INF/jspf/footer.jspf" %>
</body>
</html>