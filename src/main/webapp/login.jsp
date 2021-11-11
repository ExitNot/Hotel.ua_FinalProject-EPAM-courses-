<%--
  Created by IntelliJ IDEA.
  User: ExitNot
  Date: 11.11.2021
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Hotel</title>
</head>
<body>
    <h1>Login Page</h1>
    <form action="controller" method="post">
        <input type="hidden" name="command" value="login">
        <input name="login">
        <input type="password" name="pwd">
        <input type="submit" name="loginBtn" value="Login">
    </form>
</body>
</html>
