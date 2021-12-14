<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="createRequest.act"></c:redirect>
</c:if>

<t:wrapper>
    <script>
        let rooms = [5, 4, 3, 2, 1];

        window.onload = function () {
            document.getElementById("add_room_btn").click();

            // Make nav element active
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
            document.getElementsByClassName("nav-item")[1].className += " active"
        }

        function cloneRoom() {
            var wrapper = document.getElementById("rooms_wrapper");
            var room = document.getElementById("room_template").innerHTML;
            if (rooms.length > 0) {
                room = room.replaceAll("room", "room".concat((rooms.pop()).toString()));
                wrapper.insertAdjacentHTML('afterbegin', room);
            }
        }

        function removeRoom(id) {
            rooms.push(id.substr(4, 5));
            const elem = document.getElementById(id);
            elem.parentNode.removeChild(elem);
        }
    </script>
    <script>
        $(function () {
            var today = new Date();
            var tomorrow = new Date(today);
            tomorrow.setDate(today.getDate() + 1);
            $("#date_from").datepicker({
                minDate: 0
            });
            $("#date_to").datepicker({
                minDate: 1
            });
            $("#date_from").datepicker("setDate", today);
            $("#date_to").datepicker("setDate", tomorrow);
        });
    </script>

    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2" style="background-color: mediumslateblue">
                <h5 class="mt-3"><fmt:message key="request.label.createReservationRequest"/></h5>
            </div>
            <div class="card-body pb-0">
                <form class="mb-0" action="createRequest.act" method="post">
                    <div class="row mb-2">
                        <div class="col-2">
                            <label for="date_from"><fmt:message key="reservations.label.from"/></label>
                            <input class="form-control" type="text" name="dateFrom" id="date_from"/>
                        </div>
                        <div class="col-2">
                            <label for="date_to"><fmt:message key="reservations.label.to"/></label>
                            <input class="form-control" type="text" name="dateTo" id="date_to"/>
                        </div>
                        <div class="col-8">
                            <c:choose>
                                <c:when test="${not empty roomType}">
                                    <fmt:message key="request.label.chosenRoom"/>:
                                    <a href="#">
                                        <fmt:message key="room.class.label.${roomType.roomClass.lcName}"/> <fmt:message key="room.label.room"/>(<c:choose><c:when test="${language == 'ru'}">${roomType.parsedBedsTypeRU}</c:when><c:otherwise>${roomType.parsedBedsType}</c:otherwise></c:choose>)
                                    </a>
                                </c:when>
                                <c:otherwise>
                                    <a class="row text-center mb-2"><fmt:message key="request.label.amountOfRoomsGuests"/></a>
                                    <div id="rooms_wrapper"></div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                    <div class="row mt-3"
                         style="background-color: mediumslateblue; margin-left: -20px; margin-right: -20px">
                        <div class="col">
                            <a href="#" class="btn btn-link w-100" onclick="cloneRoom()" id="add_room_btn"
                               style="color: white">
                                <fmt:message key="request.label.addAnotherRoom"/>
                            </a>
                        </div>
                        <div class="col">
                            <input type="submit" name="request_btn" value="<fmt:message key="nav.label.makeAReservation"/>"
                                   class="btn btn-link w-100" style="color: white"/>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div id="room_template">
        <div class="row" id="room">
            <label for="amountOfAdultsInRoom"><fmt:message key="request.label.adults"/>:</label>
            <input class="form-control col-3 mr-2" type="number" name="amountOfAdultsIn_room" placeholder="<fmt:message key="request.label.adults"/>"
            min="1" max="5" value="1" onChange="guests_value()" id="amountOfAdultsInRoom"/>
            <label for="amountOfChildrenInRoom"><fmt:message key="request.label.children"/>:</label>
            <input class="form-control col-3" type="number" name="amountOfChildrenIn_room" placeholder="<fmt:message key="request.label.children"/>"
                   min="0" max="5" value="0" onChange="guests_value()" id="amountOfChildrenInRoom"/>
            <select class="col-2 h-75 mt-2 ml-2 pl-0" name="roomClass">
                <option value="1"><fmt:message key="room.class.label.standard"/></option>
                <option value="2"><fmt:message key="room.class.label.upgraded"/></option>
                <option value="3"><fmt:message key="room.class.label.deluxe"/></option>
                <option value="4"><fmt:message key="room.class.label.suite"/></option>
            </select>
            <button type="button" class="btn btn-link" id="delete_btn"
                    onclick="removeRoom('room')" style="color: mediumslateblue">
                <i class="fas fa-times"></i>
            </button>
        </div>
    </div>
</t:wrapper>