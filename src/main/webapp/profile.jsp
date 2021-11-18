<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty id}">
    <c:redirect url="signIn.jsp"></c:redirect>
</c:if>

<t:wrapper>
    <a>User: ${login}</a><br/>
    <a>Role: ${role}</a>
    <form action="logout.act" method="get">
        <input type="submit" name="logout" value="Log out">
    </form>
    <form action="deleteUser.act" method="post">
        <input type="submit" name="deleteUser" value="Delete account"
               style="background: darkred; color: aliceblue">
    </form>

    <h3>Reservations:</h3>
    <table>
        <tr>
            <th>Room number</th>
            <th>From</th>
            <th>To</th>
        </tr>
        <c:forEach var="reservation" items="${userReservationsList}">
            <tr>
                <td>${reservation.roomNumber}</td>
                <td>${reservation.from}</td>
                <td>${reservation.to}</td>
            </tr>
        </c:forEach>
    </table>
    <h3>Requests:</h3>
    <table>
        <tr>
            <th>Room number</th>
            <th>From</th>
            <th>To</th>
            <th>Status</th>
        </tr>
        <c:forEach var="request" items="${userRequestsList}">
            <tr>
                <td>${request.roomNumber}</td>
                <td>${request.from}</td>
                <td>${request.to}</td>
                <c:choose>
                    <c:when test="${request.statusName == 'canceled'}">
                        <td style="text-transform: capitalize;">${request.statusName}</td>
                    </c:when>
                    <c:otherwise>
                        <td>Waiting for ${request.statusName}</td>
                        <td>
                            <form action="cancelRequest.act" method="post" style="margin-bottom: 0">
                                <input type="hidden" name="requestId" value="${request.id}">
                                <input type="submit" value="Cancel">
                            </form>
                        </td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>
</t:wrapper>
