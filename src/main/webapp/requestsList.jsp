<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty id}">
    <c:redirect url="myRequests.act"></c:redirect>
</c:if>

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
                            <a class="col-sm-2 ml-2">Requests waiting for response</a>
                        </label>
                    </div>
                    <div class="col-3 text-right">
                        <input class="btn" type="submit" name="searchBtn" value="Search"
                               style="background-color: mediumslateblue; color: white"/>
                    </div>
                </form>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr>
                        <th scope="col">User</th>
                        <th scope="col" class="col-2">Room number</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col">Room class</th>
                        <th scope="col">Price</th>
                        <th scope="col">Status</th>
                        <th scope="col">Response</th>
                        <th scope="col">Cancel</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestsList}">
                        <tr class="text-center">
<%--                            <form action="requestResponse.act" method="post" id="requestResponse">--%>
<%--                                <input type="hidden" name="requestId" value="${request.id}"/>--%>
                                <td>${request.userEmail}</td>
                                <td>
                                        ${request.roomNumber}
<%--                                    <input class="col-3 px-1" type="number" name="roomNumber" value="${request.roomNumber}"/>--%>
                                </td>
                                <td>${request.from}</td>
                                <td>${request.to}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${not empty request.roomType}">
                                            <a class="link-info" href="#"
                                               onclick="document.getElementById('info_form${request.id}').submit();">
                                                    ${request.roomType.roomClass} with ${request.roomType.parsedBedsType}
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            <a>
                                                    ${request.rc}
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>${request.price}</td>
                                <td>${request.statusName}</td>
                                <td>
                                    <form action="request.act">
                                        <input type="hidden" name="requestId" value="${request.id}">
                                        <input type="submit" class="link" value="Response">
                                    </form>
                                </td>
                                <td>
                                    <a class="link" href="#"
                                       onclick="document.getElementById('cancel_form${request.id}').submit();">
                                        Cancel
                                    </a>
                                </td>
<%--                            </form>--%>
                        </tr>
<%--                        <form id="info_form${request.id}" action="roomType.act" method="get" style="margin-bottom: 0">--%>
<%--                            <input type="hidden" name="typeId" value="${request.roomType.id}">--%>
<%--                        </form>--%>
                        <form id="cancel_form${request.roomType.id}" action="cancelRequest.act" method="post"
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