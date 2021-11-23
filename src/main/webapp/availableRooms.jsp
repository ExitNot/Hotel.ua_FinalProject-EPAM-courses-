<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty id}">
    <c:redirect url="availableRooms.act"></c:redirect>
</c:if>

<t:wrapper>

    <script>
        $(function() {
            var today = new Date();
            var tomorrow = new Date(today);
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

    <div class="page_content">
        <h2>Choose specific room</h2>
        <form action="availableRooms.act" method="get">
            From <input type="text" name="dateFrom" id="date_from"/> to
            <input type="text" name="dateTo" id="date_to" min="2021-11-17"/>
            <input type="submit" name="search_btn" value="Search" class="button"/>
        </form>
        <c:if test="${not empty roomsList}">
            <table style="border: black">
                <tr>
                    <th>Room number</th>
                    <th>Floor</th>
                    <th>capacity</th>
                    <th>Beds types</th>
                    <th>Room class</th>
                </tr>
                <c:forEach var="room" items="${roomsList}">
                    <tr>
                        <td>${room.roomNumber}</td>
                        <td>${room.floor}</td>
                        <td>${room.capacity}</td>
                        <td>${room.bedType}</td>
                        <td>${room.roomClassName}</td>
                        <td>
                            <form action="bookSpecificRoom.act" method="post" style="margin-bottom: 0">
                                <input type="hidden" name="roomId" value="${room.id}">
                                <input type="submit" value="Make reservation">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>
            <c:remove var="roomsList"/>
        </c:if>
        <c:if test="${not empty roomsListEx}">
            <h2 style="color:red">${roomsListEx}</h2>
            <c:remove var="roomsListEx"/>
        </c:if>
    </div>
</t:wrapper>