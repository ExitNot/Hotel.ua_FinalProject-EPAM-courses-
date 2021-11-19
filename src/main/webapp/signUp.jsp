<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty login}">
    <c:redirect url="index.jsp"></c:redirect>
</c:if>

<t:wrapper>
    <script>
        const passwordPattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/

        window.onload = function () {
            var current = document.getElementsByClassName("active")[0];
            current.className = current.className.replace(" active", "");

            let pwd =  document.getElementById("pwd");
            let pwd_format =  document.getElementsByClassName("pwd_format")[0];

            pwd.addEventListener('keyup', () => {
                if (!passwordPattern.test(pwd.value)) {
                    pwd.style.borderColor = 'red'
                } else {
                    pwd.style.borderColor = 'black'
                }
            })

            pwd.addEventListener('focusout', () => {
                pwd_format.style.display = 'none';
            })

            pwd.addEventListener('focusin', () => {
                pwd_format.style.display = 'inline-block';
            })

        }
    </script>

    <h1>Sign Up Page</h1>
    <form action="signUp.act" method="post">
        <input type="hidden" name="command" value="signUp">
        <input name="name" placeholder="First name" required><br/>
        <input name="surname" placeholder="Second name" required><br/>
        <input name="login" placeholder="User name" required><br/>
        <div class="pwd_form_elem">
            <span class="pwd_format">8-15 Alphanumeric characters, at least one letter and one number</span>
            <input type="password" name="pwd" id="pwd" placeholder="Password" required><br/>
            <input type="password" name="pwdConfirmation" placeholder="Confirm password" required><br/>
        </div>
        <input name="phoneNumber" placeholder="Phone number" required><br/>
        <input type="email" name="email" placeholder="Email" required><br/>
        <input type="submit" name="signUpBtn" value="Sign Up" class="button">
    </form>
    <c:if test="${not empty signUpError}">
        <h2 style="color:red">${signUpError}</h2>
    </c:if>
</t:wrapper>
