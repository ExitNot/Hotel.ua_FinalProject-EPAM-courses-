<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty login}">
    <c:redirect url="signIn.jsp"></c:redirect>
</c:if>

<t:wrapper>
    <h1>Create Reservation</h1>

    <script>
        var rooms = 0;

        window.onload = function () {
            // document.getElementById("request").click();
            document.getElementById("add_room_btn").click();
        }

        function changeTab(id) {
            document.getElementById("page_content").innerHTML = document.getElementById(id + "_content").innerHTML;
            document.getElementById("request").className = "not_selected";
            document.getElementById("specific_room").className = "not_selected";
            document.getElementById(id).className = "selected";
        }

        function cloneRoom() {
            var wrapper = document.getElementById("rooms_wrapper");
            var room = document.getElementById("room_template").innerHTML;
            room = room.replaceAll("room", "room".concat((rooms + 1).toString()));

            if (rooms < 5) {
                rooms++;
                wrapper.insertAdjacentHTML('afterbegin', room);
            }
            // document.getElementById("page_content").innerHTML = document.getElementById("request_content").innerHTML;
        }

        function removeRoom(id) {
            rooms--;
            const elem = document.getElementById(id);
            elem.parentNode.removeChild(elem);
            // document.getElementById("page_content").innerHTML = document.getElementById("request_content").innerHTML;
        }
    </script>
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
    </script>
    <div id="reservation">
<%--        <li class="selected" id="request" onclick="changeTab(this.id);">Make request</li>--%>
<%--        <li class="not_selected" id="specific_room" onclick="changeTab(this.id);">Specify room</li>--%>

        <div class="page_content" id="request_content">
            <h2>Create request</h2>
            <form action="request.act" method="post" class="form">
                <label>
                    Dates:
                    <input type="text" name="dateFrom" id="date_from"/> -
                    <input type="text" name="dateTo" id="date_to"/>
                </label>
                <br/>
                <a>Amount of rooms and guests</a>
                <div id="rooms_wrapper">
                    <button type="button" onclick="cloneRoom()" id="add_room_btn">Add another room</button>
                </div>
                <input type="submit" name="request_btn" value="Make reservation" class="button"/>
            </form>
        </div>

        <div class="hidden_content" id="room_template">
            <div id="room">
                <label>
                    <input type="number" name="amount_of_adults" placeholder="adult"
                           max="5" value="1" onChange="guests_value()">
                    <input type="number" name="amount_of_children" placeholder="child"
                           max="5" value="0" onChange="guests_value()">
                    <select name="room_class">
                        <option value="1">standard</option>
                        <option value="2">deluxe</option>
                        <option value="3">suite</option>
                    </select>
<%--                    <input type=""> todo add room class  --%>
                    <button type="button" id="delete_btn" onclick="removeRoom('room')">
                        000<%-- todo insert icon --%></button>
                </label>
            </div>
        </div>
<%--        <div id="page_content"></div>--%>
    </div>
</t:wrapper>