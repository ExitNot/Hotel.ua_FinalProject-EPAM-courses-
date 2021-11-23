<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty id}">
    <c:redirect url="profile.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3 mr-auto">Profile</h5>
                <form action="deleteUser.act" method="post" id="delete_user">
                    <a href="#" style="color: black"
                       onclick="document.getElementById('delete_user').submit();">
                        <i class="fas fa-user-slash fa-lg float-right"></i>
                    </a>
                </form>
            </div>
            <div class="card-body pb-0">
                <form action="signUp.act"></form>
                <div class="row mb-2">
                    <div class="col-2">
                        Name:
                    </div>
                    <div class="col">
                        <input class="form-control" type="text" placeholder="${user.name}" readonly>
                    </div>
                    <div class="col">
                        <input class="form-control" type="text" placeholder="${user.surname}" readonly>
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-2">
                        Email:
                    </div>
                    <div class="col">
                        <input class="form-control" type="email" placeholder="${user.email}" readonly>
                    </div>
                </div>
                <div class="row mb-3 pb-2">
                    <div class="col-2">
                        Phone number:
                    </div>
                    <div class="col">
                        <input class="form-control" type="text" placeholder="${user.phoneNumber}" readonly>
                    </div>
                </div>
                <div class="row mt-3" style="background-color: mediumslateblue; margin-left: -20px; margin-right: -20px">
                    <div class="col">
                        <a href="logout.act" class="btn w-100" style="color: white">Logout</a>
                    </div>
                    <div class="col">
                        <a href="./editProfile.jsp" class="btn w-100" style="color: white">Edit profile</a>
                    </div>
                </div>
            </div>
        </div>
    </div>

<%--    ============================ --%>

    <a>User: ${email}</a><br/>
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
