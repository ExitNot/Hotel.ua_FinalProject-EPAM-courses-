<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:wrapper>
    <div class="page_content" id="specific_room">
        <h2>Choose specific room</h2>
        <form action="getAvailableRooms.act" method="get"></form>
<%--        <c:forEach var="room" items="${}">--%>
    </div>
</t:wrapper>