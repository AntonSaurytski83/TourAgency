<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://epam.com/taglib/custom" %>


<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="tour.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<%@include file="../WEB-INF/jspf/header.jspf" %>
<div class="content" id="tours">
    <c:forEach var="tour" items="${tours}">
        <div class="tour-view">
            <h3>${tour.tourname}</h3>
            <c:set var="type" value="${tour.type.displayName}"/>
            <p class="type type-${type}"><fmt:message key="tour_type.${type}"/><c:if test="${tour.hot}"><span
                    class="hot"><fmt:message key="tour_table.hot"/></span></c:if></p>
            <p class="details">${tour.details}</p>
            <c:set var="discounted" value="${tour.price - (tour.price * tour.regularDiscount * 0.01)}"/>
            <c:choose>
               <c:when test="${regular eq true and discounted < tour.price}">
                    <p class="price"><span class="discounted">${tour.price} USD</span> ${discounted} USD</p>
                </c:when>
                <c:otherwise>
                    <p class="price">${tour.price} USD</p>
                </c:otherwise>
            </c:choose>
            <c:choose>
                <c:when test="${not empty rolename and rolename eq 'client'}">
                    <a class="btn" href="app?c=order&id=${tour.id}&lang=${locale}"><fmt:message key="tour.order"/></a>
                </c:when>
                <c:otherwise>
                    <p class="not-logged"><fmt:message key="tour.not_logged"/></p>
                </c:otherwise>
            </c:choose>
        </div>
    </c:forEach>
</div>
<%@include file="../WEB-INF/jspf/footer.jspf" %>
</body>
</html>