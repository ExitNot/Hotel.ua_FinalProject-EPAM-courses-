<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:wrapper>
    <h1>Sign Up Page</h1>
    <form action="signup.act" method="post">
        <input type="hidden" name="command" value="login">
        <table>
            <tr>
                <td>First name:</td>
                <td><input name="name"></td>
            </tr>
            <tr>
                <td>Second name:</td>
                <td><input name="surname"></td>
            </tr>
            <tr>
                <td>User name:</td>
                <td><input name="login"></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><input type="password" name="pwd"></td>
            </tr>
            <tr>
                <td>Confirm password:</td>
                <td><input type="password" name="pwdConfirmation"></td>
            </tr>
            <tr>
                <td>Phone number:</td>
                <td><input name="phoneNumber"></td>
            </tr>
            <tr>
                <td>Email:</td>
                <td><input type="email" name="email"></td>
            </tr>
        </table>
        <input type="submit" name="signUpBtn" value="Sign Up" class="button">
    </form>
</t:wrapper>
