<%@ page language="java" pageEncoding="utf8" contentType="text/html;charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="auth" uri="http://epam.com/taglib/custom" %>


<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<c:set var="user" scope="page" value="${auth:user(pageContext.request)}"/>
<html>
<head>
    <title><fmt:message key="admin.manager.title"/></title>
    <link rel="stylesheet" href="${pageContext.servletContext.contextPath}/css/style.css"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <script type="text/javascript" src="${pageContext.servletContext.contextPath}/js/jquery.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.btn-red').click(function () {
                return !!confirm('<fmt:message key="admin.manager.delete"/>');
            })
        });
    </script>
</head>
<body>
<%@include file="../../WEB-INF/jspf/header.jspf" %>
<div class="content">
    <div class="actions">
        <a class="btn" href="app?c=add_tour&lang=${locale}"><fmt:message key="tour_table.add"/></a>
    </div>
    <c:forEach items="${tours}" var="tour">
        <div class="tour">
            <h3>${tour.tourname}
                <c:if test="${tour.hot}">
                    <span class="hot"><fmt:message key="tour_table.hot"/></span>
                </c:if>
            </h3>
            <div class="inner">
                <p><b><fmt:message key="tour_table.price"/>:</b> ${tour.price} USD</p>

                <p><b><fmt:message key="tour_table.type"/>:</b> ${tour.type}</p>

                <p><b><fmt:message key="tour_table.details"/>:</b> ${tour.details}</p>

                <p><b><fmt:message key="tour_table.discount"/>:</b> ${tour.regularDiscount}%</p>
            </div>
            <a class="btn btn-blue" href="app?c=update_tour&id=${tour.id}&lang=${locale}"><fmt:message
                    key="tour_table.edit"/></a>
            <a class="btn btn-red" href="app?c=delete_tour&id=${tour.id}&lang=${locale}"><fmt:message
                    key="tour_table.delete"/></a>
        </div>
    </c:forEach>
    <div class="actions">
        <a class="btn" href="app?c=add_tour&lang=${locale}"><fmt:message key="tour_table.add"/></a>
    </div>
</div>
<%@include file="../../WEB-INF/jspf/footer.jspf" %>
</body>
</html>