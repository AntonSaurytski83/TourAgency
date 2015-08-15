<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ftm" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${locale}"/>
<fmt:setBundle basename="message"/>
<fmt:message key="error.500.title" var="title" scope="page"/>
<fmt:message key="error.500.message" var="message" scope="page"/>
<fmt:message key="error.back" var="back" scope="page"/>
<html>
<head>
    <title>${title}</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/error.css"/>
</head>

<body>
<%-- production--%>
<h1>${title}</h1>

<p>${message}</p>
<a href="${pageContext.request.contextPath}/index.jsp?lang=${locale}">${back}</a>


<%-- development
<h4>Error:</h4>
<p>${pageContext.exception}</p>
<p>${pageContext.errorData.statusCode}</p>
<p>${pageContext.errorData.servletName}</p>

<h4>Stack trace:</h4>
<c:forEach var="trace"
           items="${pageContext.exception.stackTrace}">
    <p>${trace}</p>
</c:forEach>--%>
</body>
</html>