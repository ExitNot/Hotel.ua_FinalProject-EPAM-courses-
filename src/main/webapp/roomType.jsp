<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<t:wrapper>

    <script>
        window.onload = function () {
            // Make nav element active
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
        }

        $(function () {  // init minimal dates
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

        $("#date_from").onchange = function () {
            $("#date_to").datepicker("minDate", new Date($("#date_from").val));
            $("#date_to").datepicker("show");
        };

    </script>

    <c:choose>
        <c:when test="${empty roomType}">
            <c:redirect url="index.jsp"/>
        </c:when>
        <c:otherwise>
            <div class="container px-0 pt-4">
                <div class="card">
                    <div class="card-header">
                        <div class="d-flex justify-content-center">
                            <h5 class="mt-1">${roomType.roomClass} Room</h5>
                        </div>

                    </div>
                    <div class="card-body px-0">
                        <div class="row">
                            <div class="col-5">
                                <div class="row mb-5 ml-3">
                                    <p class="card-text">${roomType.description}</p>
                                </div>
                                <div class="row mt-2 d-flex justify-content-center">
                                    <i class="fas fa-bed mr-2"></i>
                                    Room has ${roomType.parsedBedsType} (capacity: ${roomType.capacity})
                                </div>
                            </div>
                            <div class="col-7 px-0">
                                <ul style="columns: 2">
                                    <c:forEach var="amen" items="${roomType.amenities}">
                                        <li>${amen}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="row mx-0" style="background-color: mediumslateblue">
                            <div class="col">
                                <a href="#" class="btn w-100" data-toggle="modal"
                                   data-target="#requestSpecificRoomModal"
                                   style="color: white">Book specific available room</a>
                            </div>
                        </div>

                            <%-- Carousel --%>
                        <div id="carousel" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel" data-slide-to="0" class="active"></li>
                                <c:forEach begin="1" end="${roomType.amountOfImg}" varStatus="loop">
                                    <li data-target="#carousel" data-slide-to="${loop.index}"></li>
                                </c:forEach>
                            </ol>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img class="d-block w-100" src="${roomType.imgPaths[0].path}" alt="1">
                                </div>
                                <c:forEach begin="1" end="${roomType.amountOfImg}" varStatus="loop">
                                    <div class="carousel-item">
                                        <fmt:parseNumber var="loopNum" value="${loop.count}" integerOnly="true"/>
                                        <img class="d-block w-100" src="${roomType.imgPaths[loopNum].path}" alt="${loopNum}">
                                    </div>
                                </c:forEach>
                            </div>
                            <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>

    <!-- Request specific room modal -->
    <div class="modal fade" id="requestSpecificRoomModal" tabindex="-1" role="dialog"
         aria-labelledby="ModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm mt-6" role="form">
            <div class="modal-content">
                <div class="modal-body">
                    <c:choose>
                        <c:when test="${empty id}">
                            <a class="text-center w-100">
                                You have to login first
                            </a>
                        </c:when>
                        <c:otherwise>
                            <div class="row">
                                <div class="col-6">
                                    <label for="date_from"><fmt:message key="reservations.label.from"/></label>
                                    <input class="form-control" type="text" form="requestSpecificForm"
                                           name="dateFrom" id="date_from"/>
                                </div>
                                <div class="col-6">
                                    <label for="date_to"><fmt:message key="reservations.label.to"/></label>
                                    <input class="form-control" type="text" form="requestSpecificForm"
                                           name="dateTo" id="date_to"/>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                <c:if test="${not empty id}">
                    <div class="modal-footer mb-0 py-0"
                         style="height: 40px; background-color: mediumslateblue; border-top: solid black;">
                        <b class="btn float-right mr-0" onclick="document.getElementById('requestSpecificForm').submit();"
                           style="width: 40%; color: white">Find</b>
                    </div>
                </c:if>
                <form class="my-0" id="requestSpecificForm" action="availableRooms.act" method="get">
                    <input type="hidden" name="typeId" value="${roomType.id}">
                </form>
            </div>
        </div>
    </div>

    <c:remove var="roomType"/>
</t:wrapper>
