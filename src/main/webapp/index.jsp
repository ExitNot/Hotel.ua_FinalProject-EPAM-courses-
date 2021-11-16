<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:wrapper>
    <h1>Home Page</h1>
    <c:choose>
        <c:when test="${empty login}">
            <a href="signIn.jsp" class="button">Sign in</a>
            <a href="signUp.jsp" class="button">Sign up</a>
        </c:when>
        <c:otherwise>
            <a href="profile.act" class="button">Profile</a>
            <a href="request.jsp" class="button">Make a reservation</a>
            <a href="availableRooms.act" class="button">Make a reservation for a specific room</a>
        </c:otherwise>
    </c:choose>
</t:wrapper>