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

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center">
        <div class="card col-12 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3"><fmt:message key="label.requests"/></h5>
            </div>
            <div class="card-body pb-0">
                <table class="table table-bordered">
                    <thead style="background-color: mediumslateblue; color: white">
                    <tr>
                        <th scope="col"><fmt:message key="room.label.roomNumber"/></th>
                        <th scope="col"><fmt:message key="request.label.guestsAmount"/></th>
                        <th scope="col" class="col-2"><fmt:message key="reservations.label.from"/></th>
                        <th scope="col" class="col-2"><fmt:message key="reservations.label.to"/></th>
                        <th scope="col"><fmt:message key="room.label.roomClass"/></th>
                        <th scope="col"><fmt:message key="request.label.price"/></th>
                        <th scope="col"><fmt:message key="request.label.status"/></th>
                        <th scope="col"><fmt:message key="label.action"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="request" items="${requestsList}">
                        <tr class="text-center">
                            <td>${request.roomNumber}</td>
                            <td>${request.adultsAmount + request.childrenAmount}</td>
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
                                <c:if test="${request.status.value == '2'}">
                                    <a class="link-error" href="#"
                                       onclick="document.getElementById('accept_form${request.id}').submit();">
                                        <fmt:message key="request.action.label.accept"/>
                                    </a>
                                </c:if>
                                <c:if test="${request.status.value != '3'}">
                                    <a class="link-error" href="#"
                                       onclick="document.getElementById('cancel_form${request.id}').submit();">
                                        <fmt:message key="request.action.label.delete"/>
                                    </a>
                                </c:if>
                                <c:if test="${request.status.value == '3'}">
                                    <a class="link-error" href="#"
                                       data-toggle="modal" data-target="#paymentModal${request.id}">
                                        <fmt:message key="request.action.label.pay"/>
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                        <form id="info_form${request.id}" action="roomType.act" method="get" style="margin-bottom: 0">
                            <input type="hidden" name="typeId" value="${request.roomType.id}">
                        </form>
                        <form id="accept_form${request.id}" action="acceptRequest.act" method="post" style="margin-bottom: 0">
                            <input type="hidden" name="requestId" value="${request.id}">
                        </form>
                        <form id="cancel_form${request.id}" action="cancelRequest.act" method="post"
                              style="margin-bottom: 0">
                            <input type="hidden" name="requestId" value="${request.id}">
                            <input type="hidden" name="redirect" value="true">
                        </form>
                        <!-- Payment modal -->
                        <div class="modal fade" id="paymentModal${request.id}" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
                            <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
                                <div class="modal-content">
                                    <div class="modal-body text-center py-5">
                                        <form class="px-3 mb-0" action="payment.act" method="post" id="paymentForm">
                                            <input type="hidden" name="requestId" value="${request.id}">
                                            <input class="btn" type="submit" value="<fmt:message key="request.action.label.pay"/>"
                                                   style="background-color: mediumslateblue; color: white"/>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>

</t:wrapper>