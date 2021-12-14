<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="myRequests.act"></c:redirect>
</c:if>

<script>

    window.onload = function () {
        // Make nav element active
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");
        document.getElementsByClassName("nav-item")[1].className += " active"
    }

</script>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-12 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <form action="requestsList.act" method="get" class="row w-100 h-75 mt-3">
                    <div class="col-9">
                        <label class="form-check-label">
                            <c:choose>
                                <c:when test="${empty waitingForResponse}">
                                    <input class="form-check-input col-sm-1" name="waitingForResponse" type="checkbox"
                                           value="true">
                                </c:when>
                                <c:otherwise>
                                    <input class="form-check-input col-sm-1" name="waitingForResponse" type="checkbox"
                                           value="true" checked>
                                    <c:remove var="waitingForResponse"/>
                                </c:otherwise>
                            </c:choose>
                            <a class="col-sm-2 ml-2"><fmt:message key="request.checkbox.waitingForResp"/></a>
                        </label>
                    </div>
                    <div class="col-3 text-right">
                        <input class="btn" type="submit" name="searchBtn" value="<fmt:message key="button.search"/>"
                               style="background-color: mediumslateblue; color: white"/>
                    </div>
                </form>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered text-center">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr>
                        <th scope="col"><fmt:message key="request.label.user"/></th>
                        <th scope="col" class="col-1"><fmt:message key="room.label.roomNumber"/></th>
                        <th scope="col"><fmt:message key="reservations.label.from"/></th>
                        <th scope="col"><fmt:message key="reservations.label.to"/></th>
                        <th scope="col"><fmt:message key="room.label.roomClass"/></th>
                        <th scope="col"><fmt:message key="request.label.price"/></th>
                        <th scope="col"><fmt:message key="request.label.status"/></th>
                        <th scope="col"><fmt:message key="label.action"/></th>
                        <th scope="col"><fmt:message key="label.cancel"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestsList}">
                        <tr class="text-center">
                                <td>${request.userEmail}</td>
                                <td>${request.roomNumber}</td>
                                <td>${request.from}</td>
                                <td>${request.to}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty request.roomType}">
                                            <a class="link-info" href="#"
                                               onclick="document.getElementById('info_form${request.id}').submit();">
                                                <fmt:message key="room.class.label.${request.roomType.roomClass.lcName}"/>(<c:choose><c:when test="${language == 'ru'}">${request.roomType.parsedBedsTypeRU}</c:when><c:otherwise>${request.roomType.parsedBedsType}</c:otherwise></c:choose>)
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a class="link-info">
                                                <fmt:message key="room.class.label.${request.rc.lcName}"/>
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${request.price}</td>
                                <td>
                                    <c:if test="${request.status.value != '4'}">
                                        <a><fmt:message key="request.status.label.waitingFor"/> </a>
                                    </c:if>
                                    <a style="text-transform: lowercase"><fmt:message key="request.status.label.${request.statusName}"/></a>
                                </td>
                                <td>
                                    <form action="request.act" id="requestForm${request.id}">
                                        <input type="hidden" name="requestId" value="${request.id}">
                                        <input type="hidden" name="requestRcVal"
                                               value="${empty request.rc ? request.roomType.roomClass.value : request.rc.value}"/>
                                        <c:choose>
                                            <c:when test="${request.status.value == '1'}">
                                                <a href="#" class="btn-link border-0"
                                                   onclick="document.getElementById('requestForm${request.id}').submit()">
                                                    <fmt:message key="request.label.response"/>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="btn-link border-0">
                                                    <fmt:message key="request.label.response"/>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </form>
                                </td>
                                <td>
                                    <a class="link" href="#" style="color: red"
                                       onclick="document.getElementById('cancel_form${request.id}').submit();">
                                        <fmt:message key="request.action.label.cancel"/>
                                    </a>
                                </td>
                        </tr>
                        <form id="info_form${request.id}" action="roomType.act" method="get" style="margin-bottom: 0">
                            <input type="hidden" name="typeId" value="${request.roomType.id}">
                        </form>
                        <form id="cancel_form${request.id}" action="cancelRequest.act" method="post"
                              style="margin-bottom: 0">
                            <input type="hidden" name="requestId" value="${request.id}">
                        </form>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</t:wrapper>