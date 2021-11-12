<%--
  Created by IntelliJ IDEA.
  User: ExitNot
  Date: 11.11.2021
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hotel</title>
</head>
<body>
    <h1>Login Page</h1>
    <form action="login.p" method="post">
        <input type="hidden" name="command" value="login">
        <input name="login"><br/>
        <input type="password" name="pwd"><br/>
        <input type="submit" name="loginBtn" value="Login">
    </form>
    <c:if test="${not empty loginError}">
        <h2 style="color:red">${loginError}</h2>
    </c:if>
</body>
</html>
