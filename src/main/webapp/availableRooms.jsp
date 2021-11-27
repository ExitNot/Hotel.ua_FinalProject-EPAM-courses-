<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${param.language}" scope="session"/>
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
                    <div class="col-10">
                        <div class="row">
                            <div class="col-2">
                                <label for="date_from"><fmt:message key="reservations.label.from"/></label>
                                <input class="form-control" type="text" name="dateFrom" id="date_from"/>
                            </div>
                            <div class="col-2">
                                <label for="date_to"><fmt:message key="reservations.label.to"/></label>
                                <input class="form-control" type="text" name="dateTo" id="date_to"/>
                            </div>
                            <div class="col-2">
                                <label for="floor"><fmt:message key="label.room"/></label>
                                <input class="form-control w-50" type="number" name="floor"
                                       id="floor" value="${floor}"/>
                            </div>
                        </div>
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
                                <td>${room.roomType.parsedBedsType}</td>
                                <td>${room.roomType.roomClass}</td>
                                <td>
                                    <a class="link-error" href="#"
                                       onclick="document.getElementById('cancel_form${request.id}').submit();">
                                        <fmt:message key="nav.label.makeAReservation"/>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </c:if>
            </div>
        </div>
    </div>

    <a href="#" type="hidden" id="error_modal_btn" data-toggle="modal" data-target="#errorModal"></a>
    <%--    <a href="#" id="sign_in_btn" class="button" data-toggle="modal" data-target="#signInModal"></a>--%>

    <!-- Error modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1" style="color: red">Error</h5>
                </div>
                <div class="modal-body justify-content-center">
                        ${roomsListEx}
                </div>
            </div>
        </div>
    </div>
</t:wrapper>