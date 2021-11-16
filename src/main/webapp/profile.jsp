<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty login}">
    <c:redirect url="signIn.jsp"></c:redirect>
</c:if>

<t:wrapper>
    <a>User: ${login}</a><br/>
    <a>Role: ${role}</a>

    <form action="deleteUser.act" method="post">
        <input type="submit" name="deleteUser" value="Delete account"
               style="background: darkred; color: aliceblue">
    </form>

    <h3>Reservations:</h3>
    <c:forEach var="request" items="${userRequestList}">
        <table>
            <tr>
                <th>User id</th>
                <th>Room id</th>
            </tr>
            <tr>
                <td>${request.userId}</td>
                <td>${request.roomId}</td>
            </tr>
        </table>
    </c:forEach>

</t:wrapper>
