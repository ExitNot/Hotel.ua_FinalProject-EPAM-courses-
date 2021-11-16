<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty login}">
    <c:redirect url="index.jsp"></c:redirect>
</c:if>

<t:wrapper>
    <h1>Sign In Page</h1>
    <form action="login.act" method="get">
        <input type="hidden" name="command" value="signIn"/>
        <c:choose>
            <c:when test="${not empty login}">
                <input name="login" value="${login}"/><br/>
            </c:when >
            <c:otherwise>
                <input name="login" placeholder="Login"/><br/>
            </c:otherwise>
        </c:choose>
        <input type="password" name="pwd" placeholder="Password"/><br/>
        <input type="submit" name="sign_in_btn" value="Sign In" class="button"/>
    </form>
    <c:if test="${not empty loginError}">
        <h2 style="color:red">${loginError}</h2>
        <c:remove var="loginError"/>
    </c:if>
</t:wrapper>