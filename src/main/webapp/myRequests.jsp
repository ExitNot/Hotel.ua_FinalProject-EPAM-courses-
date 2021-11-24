<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${empty id}">
    <c:redirect url="myRequests.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3">Requests</h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr>
                        <th scope="col">Room number</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col">Room class</th>
                        <th scope="col">Price</th>
                        <th scope="col">Status</th>
                        <th scope="col">Cancel</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestsList}">
                        <tr class="text-center">
                            <td>${request.roomNumber}</td>
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
                                        <a class="link-info" href="#">
                                                ${request.rc}
                                        </a>
                                    </c:otherwise>
                                </c:choose>

                            </td>
                            <td>${request.price}</td>
                            <td>${request.statusName}</td>
                            <td>
                                <a class="link-error" href="#"
                                   onclick="document.getElementById('cancel_form${request.id}').submit();">
                                    Cancel
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
            </div>
        </div>
    </div>
</t:wrapper>