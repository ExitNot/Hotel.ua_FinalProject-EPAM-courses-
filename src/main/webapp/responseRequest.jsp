<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="requestsResponse.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3">Request</h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered w-100">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr class="text-center">
                        <th scope="col">User</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col">Room number</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="text-center">
                        <td>${request.userEmail}</td>
                        <td>${request.from}</td>
                        <td>${request.to}</td>
                        <td>
                            <c:choose>
                                <c:when test="${request.roomNumber == 0}">
                                    <a href="#" id="choose_room_btn" class="link" data-toggle="modal"
                                       data-target="#chooseRoomModal">
                                        Choose
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    ${request.roomNumber}
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                    </tbody>
                </table>
                <div class="row mt-3"
                     style="background-color: mediumslateblue; margin-left: -20px; margin-right: -20px">
                    <a href="requestsResponse.act" class="btn w-100" style="color: white">Send</a>
                </div>
            </div>
        </div>
    </div>

    <!-- choose room modal -->
    <div class="modal fade" id="chooseRoomModal" tabindex="-1" role="dialog"
         aria-labelledby="ModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-xl" style="max-width: 80%;" role="form">
            <div class="modal-content">
                <div class="modal-body">
                    <c:choose>
                        <c:when test="${empty roomsList}">
                            <h3>No such rooms available</h3>
                        </c:when>
                        <c:otherwise>
                            <table class="table table-bordered text-center">
                                <thead style="background-color: mediumslateblue; color: white">
                                <tr>
                                    <th class="col-1"><fmt:message key="room.label.roomNumber"/></th>
                                    <th class="col-1"><fmt:message key="room.label.floor"/></th>
                                    <th class="col-4"><fmt:message key="room.label.beds"/></th>
                                    <th class="col-3"><fmt:message key="room.label.roomClass"/></th>
                                    <th class="col-3">Choose</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach var="room" items="${roomsList}">
                                    <tr class="text-center">
                                        <td>${room.roomNumber}</td>
                                        <td>${room.floor}</td>
                                        <td>${room.roomType.parsedBedsType}</td>
                                        <td>${room.roomType.roomClass}</td>
                                        <td>
                                            <form action="request.act">
                                                <input type="hidden" name="assignedRoomId" value="${room.id}">
                                                <input type="submit" class="link" value="Choose">
                                            </form>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
<%--                <div class="modal-footer">--%>
<%--                    <a class="col-l-5 mr-3" href="#"><fmt:message key="acc.link.forgotPwd"/>?</a>--%>
<%--                    <button type="submit" form="signInForm" class="btn btn-primary col-5">--%>
<%--                        <fmt:message key="button.signIn"/>--%>
<%--                    </button>--%>
<%--                </div>--%>
            </div>
        </div>
    </div>
</t:wrapper>
