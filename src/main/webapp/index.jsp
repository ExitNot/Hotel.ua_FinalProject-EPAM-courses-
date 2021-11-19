<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<script>
    window.onload = function () {
        <c:if test="${not empty loginError}">
            document.getElementById("sign_in_btn").click();
        </c:if>
    }
</script>

<t:wrapper>

    <h5 class="mt-3 align-content-center">Hotel room types</h5>
    <hr/>

    <div class="container">
        <c:forEach var="type" items="${roomTypesList}">
            <div class="card mb-2" style="background-color: #ede6b4; border: solid #322325">  <%-- One room type --%>
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
                        <div class="card-footer mb-0 py-0" style="height: 40px; background-color: #5d325f; border-top: solid #322325;">
                            <a href="#" class="btn float-right mr-0" style="width: 40%; color: white">Make a reservation</a>
                            <a>     </a>
                            <a href="#" class="btn float-right mr-1" style="width: 20%; color: white">Info</a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>

    </div>
</t:wrapper>