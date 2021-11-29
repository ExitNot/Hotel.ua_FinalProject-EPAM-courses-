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
                        <th scope="col">Guests amount</th>
                        <th scope="col">From</th>
                        <th scope="col">To</th>
                        <th scope="col">Room class</th>
                        <th scope="col">Price</th>
                        <th scope="col">Status</th>
                        <th scope="col">Action</th>
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
                                <c:if test="${request.statusName == 'Waiting for accept'}">
                                    <a class="link-error" href="#"
                                       onclick="document.getElementById('accept_form${request.id}').submit();">
                                        Accept
                                    </a>
                                </c:if>
                                <c:if test="${request.statusName != 'Payment'}">
                                    <a class="link-error" href="#"
                                       onclick="document.getElementById('cancel_form${request.id}').submit();">
                                        Cancel
                                    </a>
                                </c:if>
                                <c:if test="${request.statusName == 'Payment'}">
                                    <a class="link-error" href="#"
                                       data-toggle="modal" data-target="#paymentModal${request.id}">
                                        Pay
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
                        </form>
                        <!-- Payment modal -->
                        <div class="modal fade" id="paymentModal${request.id}" tabindex="-1" role="dialog"
                             aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
                            <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
                                <div class="modal-content">
                                    <div class="modal-body text-center py-5">
                                        <form class="px-3 mb-0" action="payment.act" method="post" id="paymentForm">
                                            <input type="hidden" name="requestId" value="${request.id}">
                                            <input class="btn" type="submit" value="Pay"
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