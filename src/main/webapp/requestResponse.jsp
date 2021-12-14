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

    function choose(reqId, roomId, roomNumber) {
        document.getElementById("hiddenRoomId" + reqId).value = roomId;
        document.getElementById("hiddenRoomNumber" + reqId).value = roomNumber;

        var td = document.getElementById("choose_td" + reqId);
        td.removeChild(document.getElementById("choose_room_btn" + reqId));
        var aTag = document.createElement("a");
        aTag.setAttribute("href", "#");
        aTag.setAttribute("id", "choose_room_btn" + reqId);
        aTag.setAttribute("class", "link");
        aTag.setAttribute("data-toggle", "modal");
        aTag.setAttribute("data-target", "#chooseRoomModal" + reqId);
        aTag.append(roomNumber);
        td.appendChild(aTag)

        $('#chooseRoomModal' + reqId).modal('toggle');
    }

</script>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-12 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3"><fmt:message key="label.request"/></h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered w-100">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr class="text-center">
                        <th scope="col" class="col-2"><fmt:message key="request.label.user"/></th>
                        <th scope="col"><fmt:message key="reservations.label.from"/></th>
                        <th scope="col"><fmt:message key="reservations.label.to"/></th>
                        <th scope="col" class="col-1"><fmt:message key="request.label.guestsAmount"/></th>
                        <th scope="col"><fmt:message key="room.label.roomClass"/></th>
                        <th scope="col" class="col-2"><fmt:message key="request.label.price"/></th>
                        <th scope="col"><fmt:message key="request.label.status"/></th>
                        <th scope="col"><fmt:message key="room.label.roomNumber"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestBundle}">
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
                                            <fmt:message key="room.class.label.${request.roomType.roomClass.lcName}"/>(<c:choose><c:when test="${language == 'ru'}">${request.roomType.parsedBedsTypeRU}</c:when><c:otherwise>${request.roomType.parsedBedsType}</c:otherwise></c:choose>)
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        <a>
                                            <fmt:message key="room.class.label.${request.rc.lcName}"/>
                                        </a>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td>
                                <input class="form-control" type="number" name="price${request.id}"
                                       form="responseRequestForm" value="${request.price}">
                            </td>
                            <td>
                                <c:if test="${request.status.value != '4'}">
                                    <a><fmt:message key="request.status.label.waitingFor"/> </a>
                                </c:if>
                                <a style="text-transform: lowercase"><fmt:message key="request.status.label.${request.statusName}"/></a>
                            </td>
                            <td id="choose_td${request.id}">
                                <c:choose>
                                    <c:when test="${request.roomNumber == 0}">
                                        <a href="#" id="choose_room_btn${request.id}" class="link" data-toggle="modal"
                                           data-target="#chooseRoomModal${request.id}">
                                            <fmt:message key="request.label.choose"/>
                                        </a>
                                    </c:when>
                                    <c:otherwise>
                                        ${request.roomNumber}
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="row mt-3"
                     style="background-color: mediumslateblue; margin-left: -20px; margin-right: -20px">
                    <a class="btn w-100" style="color: white"
                       onclick="document.getElementById('responseRequestForm').submit();"><fmt:message key="request.label.send"/></a>
                </div>
            </div>
        </div>
    </div>

    <form id="responseRequestForm" action="requestResponse.act" method="post">
        <c:set var="requestBundle" value="${requestBundle}" scope="session"/>
        <c:forEach items="${requestBundle}" var="request">
            <input id="hiddenRoomId${request.id}" type="hidden" name="chosenRoomId${request.id}"/>
            <input id="hiddenRoomNumber${request.id}" type="hidden" name="chosenRoomNumber${request.id}"/>
        </c:forEach>
    </form>

    <c:forEach var="request" items="${requestBundle}">
        <!-- choose room modal -->
        <div class="modal fade" id="chooseRoomModal${request.id}" tabindex="-1" role="dialog"
             aria-labelledby="ModalCenterTitle" aria-hidden="false">
            <div class="modal-dialog modal-xl" style="max-width: 80%;" role="form">
                <div class="modal-content">
                    <div class="modal-body">
                        <c:choose>
                            <c:when test="${empty request.roomList}">
                                <h3><fmt:message key="label.noSuchRoom"/></h3>
                            </c:when>
                            <c:otherwise>
                                <table class="table table-bordered text-center">
                                    <thead style="background-color: mediumslateblue; color: white">
                                    <tr>
                                        <th class="col-1"><fmt:message key="room.label.roomNumber"/></th>
                                        <th class="col-1"><fmt:message key="room.label.floor"/></th>
                                        <th class="col-4"><fmt:message key="room.label.beds"/></th>
                                        <th class="col-3"><fmt:message key="room.label.roomClass"/></th>
                                        <th class="col-3"><fmt:message key="request.label.choose"/></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach var="room" items="${request.roomList}">
                                        <tr class="text-center">
                                            <td>${room.roomNumber}</td>
                                            <td>${room.floor}</td>
                                            <td><c:choose><c:when test="${language == 'ru'}">${room.roomType.parsedBedsTypeRU}</c:when><c:otherwise>${room.roomType.parsedBedsType}</c:otherwise></c:choose></td>
                                            <td><fmt:message key="room.class.label.${room.roomType.roomClass.lcName}"/></td>
                                            <td>
                                                <a href="#" class="btn-link border-0"
                                                   onclick="choose(${request.id}, ${room.id}, ${room.roomNumber})"><fmt:message key="request.label.choose"/></a>
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
    </c:forEach>

    <a href="#" type="hidden" id="error_modal_btn" data-toggle="modal" data-target="#errorModal"></a>

    <!-- Error modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle"
         aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1" style="color: red"><fmt:message key="label.error"/></h5>
                </div>
                <div class="modal-body justify-content-center">
                        ${requestResponseEx}
                </div>
                <c:remove var="requestResponseEx"/>
            </div>
        </div>
    </div>
</t:wrapper>
