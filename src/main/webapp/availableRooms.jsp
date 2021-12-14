<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="availableRooms.act"></c:redirect>
</c:if>

<t:wrapper>

    <script>
        window.onload = function () {
            // Make nav element active
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
            document.getElementsByClassName("nav-item")[2].className += " active"
            <c:if test="${not empty roomsListEx}">
                document.getElementById("error_modal_btn").click();
            </c:if>
            <c:if test="${not empty roomClass}">
                document.getElementById('roomClass').value = ${roomClass};
            </c:if>
        }

        $(function () {  // init minimal dates
            var today;
            var tomorrow;

            if (${not empty dateFrom} && ${empty roomsListEx}) {
                today = new Date('${dateFrom}');
            } else {
                today = new Date();
            }
            if (${not empty dateTo} && ${empty roomsListEx}) {
                tomorrow = new Date('${dateTo}');
            } else {
                tomorrow = new Date(today);
                tomorrow.setDate(today.getDate() + 1);
            }

            $("#date_from").datepicker({
                minDate: 0
            });
            $("#date_to").datepicker({
                minDate: 1
            });
            $("#date_from").datepicker("setDate", today);
            $("#date_to").datepicker("setDate", tomorrow);
        });

        $("#date_from").onchange = function () {
            $("#date_to").datepicker("minDate", new Date($("#date_from").val));
            $("#date_to").datepicker("show");
        };

    </script>

    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-11 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <form action="availableRooms.act" method="get" class="row w-100 h-75 mt-3">
                    <div class="col-9">
                        <div class="row">
                            <div class="col-2">
                                <label for="date_from"><fmt:message key="reservations.label.from"/></label>
                                <input class="form-control px-1" type="text" name="dateFrom" id="date_from"/>
                            </div>
                            <div class="col-2">
                                <label for="date_to"><fmt:message key="reservations.label.to"/></label>
                                <input class="form-control px-1" type="text" name="dateTo" id="date_to"/>
                            </div>
                            <div class="col-2">
                                <div class="row">
                                    <div class="col-6 pr-0">
                                        <label for="floor"><fmt:message key="room.label.floor"/></label>
                                        <input class="form-control" type="number" name="floor"
                                               id="floor" value="${empty floor ? 0 : floor}"/>
                                    </div>
                                    <div class="col-6 pr-0">
                                        <label for="floor"><fmt:message key="room.label.capacity"/></label>
                                        <input class="form-control" type="number" min="0" max="4" name="capacity"
                                               id="capacity" value="${empty capacity ? 0 : capacity}"/>
                                    </div>
                                </div>
                            </div>
                            <div class="col-2 pl-4">
                                <select class="form-select" name="roomClass" id="roomClass"
                                        style="margin-top: 35px"
                                        aria-label=".form-select-lg example">
                                    <option value="0" ${roomClass == '0' ? 'selected' : '' }><fmt:message key="room.label.roomClass"/></option>
                                    <option value="1" ${roomClass == '1' ? 'selected' : '' }><fmt:message key="room.class.label.standard"/></option>
                                    <option value="2" ${roomClass == '2' ? 'selected' : '' }><fmt:message key="room.class.label.upgraded"/></option>
                                    <option value="3" ${roomClass == '3' ? 'selected' : '' }><fmt:message key="room.class.label.deluxe"/></option>
                                    <option value="4" ${roomClass == '4' ? 'selected' : '' }><fmt:message key="room.class.label.suite"/></option>
                                </select>
                            </div>
                        </div>
                    </div>
                    <div class="col-1">
                        <a href="./availableRooms.act" style="color: black"
                           onclick="<c:remove var="capacity"/><c:remove var="floor"/><c:remove var="roomClass"/><c:remove var="dateTo"/><c:remove var="dateFrom"/>"
                           data-bs-toggle="tooltip" data-bs-placement="bottom" title="<fmt:message key="tooltip.RemoveFilters"/>">
                            <i class="fas fa-trash-alt fa-lg float-right"></i>
                        </a>
                    </div>
                    <div class="col-2">
                        <input class="btn mt-3" type="submit" name="searchBtn" value="<fmt:message key="button.search"/>"
                               style="background-color: mediumslateblue; color: white"/>
                    </div>
                </form>
            </div>
            <div class="card-body pb-0">
                <c:if test="${not empty roomType}">
                    <div class="row text-center">
                        <div class="w-100">
                            <a><fmt:message key="room.label.only"/> </a>
                            <a style="text-transform: lowercase"><fmt:message key="room.class.label.${roomType.roomClass.lcName}"/> </a>
                            <a><fmt:message key="room.label.room"/>(<c:choose><c:when test="${language == 'ru'}">${roomType.parsedBedsTypeRU}</c:when><c:otherwise>${roomType.parsedBedsType}</c:otherwise></c:choose>)</a>
                        </div>
                    </div>
                    <c:remove var="roomType"/>
                </c:if>
                <c:choose>
                    <c:when test="${empty roomsList}">
                        <fmt:message key="label.noSuchRoom"/>
                    </c:when>
                    <c:otherwise>
                        <table class="table table-bordered text-center">
                            <thead style="background-color: mediumslateblue; color: white">
                            <tr>
                                <th class="col-1"><fmt:message key="room.label.roomNumber"/></th>
                                <th class="col-1"><fmt:message key="room.label.floor"/></th>
                                <th class="col-4"><fmt:message key="room.label.beds"/></th>
                                <th class="col-3"><fmt:message key="room.label.roomClass"/></th>
                                <th class="col-3"><fmt:message key="label.reservation"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach var="room" items="${roomsList}">
                                <tr class="text-center">
                                    <td>${room.roomNumber}</td>
                                    <td>${room.floor}</td>
                                    <c:choose>
                                        <c:when test="${language == 'ru'}"><td>${room.roomType.parsedBedsTypeRU}</td></c:when>
                                        <c:otherwise><td>${room.roomType.parsedBedsType}</td></c:otherwise>
                                    </c:choose>
                                    <td><fmt:message key="room.class.label.${room.roomType.roomClass.lcName}"/></td>
                                    <td>
                                        <a class="link" href="#"
                                           data-toggle="modal" data-target="#requestModal${room.id}">
                                            <fmt:message key="nav.label.makeAReservation"/>
                                        </a>
                                    </td>
                                </tr>
                                <!-- Request modal -->
                                <div class="modal fade" id="requestModal${room.id}" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
                                    <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
                                        <div class="modal-content border-0">
                                            <div class="modal-body justify-content-center">
                                                <form action="./createRequest.act" name="createRequest" method="post"
                                                        id="createRequest${room.id}">
                                                    <input type="hidden" name="roomId" value="${room.id}">
                                                    <input type="hidden" name="dateFrom" value="${dateFrom}">
                                                    <input type="hidden" name="dateTo" value="${dateTo}">
                                                    <div class="row">
                                                        <div class="col">
                                                            <label for="amountOfAdultsInRoom"><fmt:message key="request.label.adults"/>:</label>
                                                            <input class="form-control" type="number" name="amountOfAdultsIn_room" placeholder="<fmt:message key="request.label.adults"/>"
                                                                   min="1" max="5" value="1" onChange="guests_value()" id="amountOfAdultsInRoom"/>
                                                        </div>
                                                        <div class="col">
                                                            <label for="amountOfChildrenInRoom"><fmt:message key="request.label.children"/>:</label>
                                                            <input class="form-control" type="number" name="amountOfChildrenIn_room" placeholder="<fmt:message key="request.label.children"/>"
                                                                   min="0" max="5" value="0" onChange="guests_value()" id="amountOfChildrenInRoom"/>
                                                        </div>
                                                    </div>
                                                </form>
                                            </div>
                                            <div class="modal-footer" style="background-color: mediumslateblue">
                                                <a class="link" href="#" style="color: white"
                                                   onclick="document.getElementById('createRequest${room.id}').submit();">
                                                    <fmt:message key="nav.label.makeAReservation"/>
                                                </a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>

    <a href="#" type="hidden" id="error_modal_btn" data-toggle="modal" data-target="#errorModal"></a>

    <!-- Error modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1" style="color: red"><fmt:message key="label.error"/></h5>
                </div>
                <div class="modal-body justify-content-center">
                        ${roomsListEx}
                </div>
            </div>
        </div>
    </div>
</t:wrapper>