<%--
  Created by IntelliJ IDEA.
  User: ExitNot
  Date: 03.11.2021
  Time: 14:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/core.css" type="text/css">
<head>
    <title>Hotel</title>
</head>
<body>
    <h1>Home Page</h1>
    <c:choose>
        <c:when test="${empty login}">
            <a href="signIn.jsp" class="button">Sign in</a>
            <a href="signUp.jsp" class="button">Sign up</a>
        </c:when>
        <c:otherwise>
            <a>User: ${login}</a><br/>
            <a>Role: ${role}</a>
            <a href="request.jsp" class="button">Make a reservation</a>
            <form action=""></form>
            <a href="availableRoomsList.act" class="button">Make a reservation for a specific room</a>
        </c:otherwise>
    </c:choose>
</body>
</html>