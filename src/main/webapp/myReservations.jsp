<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="myReservations.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3"><fmt:message key="label.reservations"/></h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr>
                        <th scope="col"><fmt:message key="room.label.roomNumber"/></th>
                        <th scope="col"><fmt:message key="reservations.label.from"/></th>
                        <th scope="col"><fmt:message key="reservations.label.to"/></th>
                        <th scope="col"><fmt:message key="room.label.roomClass"/></th>
                        <th scope="col"><fmt:message key="label.cancel"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="reservation" items="${userReservationsList}">
                        <tr class="text-center">
                            <td>${reservation.roomNumber}</td>
                            <td>${reservation.from}</td>
                            <td>${reservation.to}</td>
                            <td>
                                <a class="link-info" href="#"
                                   onclick="document.getElementById('info_form${reservation.id}').submit();">
                                    <fmt:message key="room.class.label.${reservation.roomType.roomClass.lcName}"/>(<c:choose><c:when test="${language == 'ru'}">${reservation.roomType.parsedBedsTypeRU}</c:when><c:otherwise>${reservation.roomType.parsedBedsType}</c:otherwise></c:choose>)
                                </a>
                            </td>
                            <td>
                                <a class="link-error" href="#"
                                   onclick="document.getElementById('cancel_form${reservation.id}').submit();">
                                    <fmt:message key="request.action.label.cancel"/>
                                </a>
                            </td>
                        </tr>
                        <form id="info_form${reservation.id}" action="roomType.act" method="get"
                              style="margin-bottom: 0">
                            <input type="hidden" name="typeId" value="${reservation.roomType.id}">
                        </form>
                        <form id="cancel_form${reservation.roomType.id}" action="cancelReservation.act" method="post"
                              style="margin-bottom: 0">
                            <input type="hidden" name="reservationId" value="${reservation.id}">
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</t:wrapper>