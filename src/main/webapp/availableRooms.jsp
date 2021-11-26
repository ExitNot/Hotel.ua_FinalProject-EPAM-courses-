<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

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
        }

        $(function() {  // init minimal dates
            var today = new Date();
            var tomorrow = new Date(today);
            console.log(new Date(${dateTo}))
            tomorrow.setDate(today.getDate() + 1);
            $( "#date_from" ).datepicker({
                minDate: 0
            });
            $( "#date_to" ).datepicker({
                minDate: 1
            });
            $( "#date_from" ).datepicker("setDate", today);
            $( "#date_to" ).datepicker("setDate", tomorrow);
        });

        $( "#date_from" ).onchange = function () {
            $( "#date_to" ).datepicker("minDate", new Date($( "#date_from" ).val));
            $( "#date_to" ).datepicker("show");
        };
    </script>

    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-11 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                    <form action="availableRooms.act" method="get" class="row w-100 h-75 mt-3">
                        <div class="col-10">
                            <div class="row">
                                <div class="col-2">
                                    <label for="date_from">From</label>
                                    <input class="form-control" type="text" name="dateFrom" id="date_from"/>
                                </div>
                                <div class="col-2">
                                    <label for="date_to">To</label>
                                    <input class="form-control" type="text" name="dateTo" id="date_to"/>
                                </div>
                                <div class="col-2">
                                    <label for="floor">Floor</label>
                                    <input class="form-control w-50" type="number" name="floor"
                                           id="floor" value="${floor}"/>
                                </div>
                            </div>

<%--                                <select class="col-6 h-75 mt-2 ml-2" name="roomClass">--%>
<%--                                    <option selected>Floor</option>--%>
<%--                                    <option value="1">1</option>--%>
<%--                                    <option value="2">2</option>--%>
<%--                                    <option value="3">3</option>--%>
<%--                                    <option value="4">4</option>--%>
<%--                                </select>--%>
                        </div>
                        <div class="col-2">
                            <input class="btn" type="submit" name="searchBtn" value="Search"
                                   style="background-color: mediumslateblue; color: white"/>
                        </div>
                    </form>
            </div>
            <div class="card-body pb-0">
                <c:if test="${not empty roomsList}">
                    <table class="table table-bordered text-center">
                        <thead style="background-color: mediumslateblue; color: white">
                        <tr>
                            <th class="col-1">Room number</th>
                            <th class="col-1">Floor</th>
                            <th class="col-4">Beds</th>
                            <th class="col-3">Room class</th>
                            <th class="col-3">Reservation</th>
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
                                    <a class="link-error" href="#"
                                       onclick="document.getElementById('cancel_form${request.id}').submit();">
                                        Make a reservation
                                    </a>
                                </td>
                            </tr>
                            <form id="info_form${request.id}" action="roomType.act" method="get" style="margin-bottom: 0">
                                <input type="hidden" name="typeId" value="${request.roomType.id}">
                            </form>
                            <form id="cancel_form${request.roomType.id}" action="cancelRequest.act" method="post"
                                  style="margin-bottom: 0">
                                <input type="hidden" name="requestId" value="${request.id}">
                            </form>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>
</t:wrapper>