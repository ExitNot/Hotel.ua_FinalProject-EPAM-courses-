<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    window.onload = function () {
        <c:if test="${not empty loginError}">
            document.getElementById("sign_in_btn").click();
        </c:if>
    }
</script>

<t:wrapper>
    <div class="container">
        <h1>Home Page</h1>
    </div>
</t:wrapper>