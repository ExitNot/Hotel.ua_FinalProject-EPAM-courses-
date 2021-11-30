<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="requestResponse.act"></c:redirect>
</c:if>

<script>

    window.onload = function () {
        <c:if test="${not empty requestResponseEx}">
        document.getElementById("error_modal_btn").click();
        </c:if>
    }

    function choose(id, roomNumber) {
        document.getElementById("hiddenRoomId").value = id;
        document.getElementById("hiddenRoomNumber").value = roomNumber;
        var aTag = document.createElement("a");
        aTag.append(roomNumber);
        document.getElementById("choose_room_btn").replaceWith(aTag);
        $('#chooseRoomModal').modal('toggle');
    }

</script>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-12 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3">Request</h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered w-100">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr class="text-center">
                        <th scope="col" class="col-2">User</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col" class="col-1">Guests amount</th>
                        <th scope="col">Room class</th>
                        <th scope="col" class="col-2">Price</th>
                        <th scope="col">Status</th>
                        <th scope="col">Room number</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="text-center">
                        <td>${request.userEmail}</td>
                        <td>${request.from}</td>
                        <td>${request.to}</td>
                        <td>${request.guestsAmount}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty request.roomType}">
                                    <a class="link-info" href="#"
                                       onclick="document.getElementById('info_form${request.id}').submit();">
                                            ${request.roomType.roomClass} with ${request.roomType.parsedBedsType}
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a>
                                            ${request.rc}
                                    </a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <input class="form-control" type="number" name="price"
                                   form="responseRequestForm" value="${request.price}">
                        </td>
                        <td>${request.statusName}</td>
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
                    <a class="btn w-100" style="color: white"
                       onclick="document.getElementById('responseRequestForm').submit();">Send</a>
                </div>
            </div>
        </div>
    </div>

    <form id="responseRequestForm" action="requestResponse.act" method="post">
        <input type="hidden" name="requestId" value="${request.id}"/>
        <input id="hiddenRoomId" type="hidden" name="chosenRoomId"/>
        <input id="hiddenRoomNumber" type="hidden" name="chosenRoomNumber"/>
    </form>

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
                                            <a href="#" class="btn-link border-0"
                                               onclick="choose(${room.id}, ${room.roomNumber})">Choose</a>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>

    <a href="#" type="hidden" id="error_modal_btn" data-toggle="modal" data-target="#errorModal"></a>

    <!-- Error modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1" style="color: red">Error</h5>
                </div>
                <div class="modal-body justify-content-center">
                        ${requestResponseEx}
                </div>
                <c:remove var="requestResponseEx"/>
            </div>
        </div>
    </div>
</t:wrapper>
