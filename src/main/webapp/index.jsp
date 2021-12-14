<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<script>
    window.onload = function () {
        <c:if test="${not empty loginEx}">
            document.getElementById("sign_in_btn").click();
        </c:if>
        <c:if test="${not empty indexNotification}">
            document.getElementById("notification_modal_btn").click();
        </c:if>
    }
</script>

<t:wrapper>

    <div class="d-flex justify-content-center">
        <h5 class="mt-3"><fmt:message key="label.ourRC"/></h5>
    </div>
    <hr/>
    <div class="container">
        <c:forEach var="type" items="${roomTypesList}">
            <div class="card mb-3" style="background-color: white; border: solid black">  <%-- One room type --%>
                <div class="row no-gutters" style="height: auto">
                    <div class="col-5">
                        <img class="card-img-left" src="${type.imgPaths[0].path}" alt="Card image cap">
                    </div>
                    <div class="col">
                        <div class="card-block px-2" style="height: 83%">
                            <div class="card-title mt-3 mb-1">
                                <fmt:message key="room.class.label.${type.roomClass.lcName}"/> <fmt:message key="room.label.room"/>(<c:choose><c:when test="${language == 'ru'}">${type.parsedBedsTypeRU}</c:when><c:otherwise>${type.parsedBedsType}</c:otherwise></c:choose>)
                            </div>
                            <hr class="mt-1"/>
                            <p class="card-text">${type.description}</p>
                        </div>
                        <div class="card-footer mb-0 py-0" style="height: 40px; background-color: mediumslateblue; border-top: solid black;">
                            <form id="info_form${type.id}" action="roomType.act" method="get" style="margin-bottom: 0">
                                <input type="hidden" name="typeId" value="${type.id}">
                                <b class="btn float-right mr-1" onclick="document.getElementById('info_form${type.id}').submit();"
                                   style="width: 20%; color: white"><fmt:message key="nav.label.info"/></b>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>

    <a href="#" type="hidden" id="notification_modal_btn" data-toggle="modal" data-target="#notificationModal"></a>

    <!-- Error modal -->
    <div class="modal fade" id="notificationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1"><fmt:message key="label.notification"/></h5>
                </div>
                <div class="modal-body justify-content-center">
                        ${indexNotification}
                    <c:remove var="indexNotification"/>
                </div>
            </div>
        </div>
    </div>
</t:wrapper>