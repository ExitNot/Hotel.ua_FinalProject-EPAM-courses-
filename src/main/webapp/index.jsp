<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    window.onload = function () {
        <c:if test="${not empty loginEx}">
            document.getElementById("sign_in_btn").click();
        </c:if>
    }
</script>

<t:wrapper>

    <div class="d-flex justify-content-center">
        <h5 class="mt-3">Our room types</h5>
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
                                ${type.roomClass} room with ${type.parsedBedsType}
                            </div>
                            <hr class="mt-1"/>
                            <p class="card-text">${type.description}</p>
                        </div>
                        <div class="card-footer mb-0 py-0" style="height: 40px; background-color: mediumslateblue; border-top: solid black;">
                            <form id="request_form${type.id}" action="request.act" method="post" style="margin-bottom: 0">
                                <input type="hidden" name="typeId" value="${type.id}">
                                <b class="btn float-right mr-0" onclick="document.getElementById('request_form${type.id}').submit();"
                                   style="width: 40%; color: white">Make a reservation</b>
                            </form>
                            <a>     </a>
                            <form id="info_form${type.id}" action="roomType.act" method="get" style="margin-bottom: 0">
                                <input type="hidden" name="typeId" value="${type.id}">
                                <b class="btn float-right mr-1" onclick="document.getElementById('info_form${type.id}').submit();"
                                   style="width: 20%; color: white">Info</b>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
</t:wrapper>