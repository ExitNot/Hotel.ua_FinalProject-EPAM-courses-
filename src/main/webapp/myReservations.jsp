<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty id}">
    <c:redirect url="myReservations.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3 mr-auto">Reservations</h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-striped">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr>
                        <th scope="col">Room number</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col">Room class</th>
                        <th scope="col">Cancel</th>
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
                                        ${reservation.roomType.roomClass} with ${reservation.roomType.parsedBedsType}
                                </a>
                            </td>
                            <td>
                                <a class="link-error" href="#"
                                   onclick="document.getElementById('cancel_form${reservation.id}').submit();">
                                    Cancel
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