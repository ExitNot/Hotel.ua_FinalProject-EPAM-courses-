<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:wrapper>

    <c:choose>
        <c:when test="${empty roomType}">
            <c:redirect url="index.jsp"/>
        </c:when>
        <c:otherwise>
            <div class="container px-0">
                <div class="card">
                    <div class="card-header">
                        <div class="d-flex justify-content-center">
                            <h5 class="mt-1">${roomType.roomClass} Room</h5>
                        </div>

                    </div>
                    <div class="card-body px-0">
                        <div class="row">
                            <div class="col-5">
                                <div class="row mb-5 ml-3">
                                    <p class="card-text">${roomType.description}</p>
                                </div>
                                <div class="row mt-2 d-flex justify-content-center">
                                    <i class="fas fa-bed mr-2"></i>
                                    Room has ${roomType.parsedBedsType} (capacity: ${roomType.capacity})
                                </div>
                            </div>
                            <div class="col-7 px-0">
                                <ul style="columns: 2">
                                    <c:forEach var="amen" items="${roomType.amenities}">
                                        <li>${amen}</li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="row mx-0" style="background-color: mediumslateblue">
                            <div class="col">
                                <a href="#" class="btn w-100" style="color: white">Make a reservation request</a>
                            </div>
                            <div class="col">
                                <a href="#" class="btn w-100" style="color: white">Book specific available room</a>
                            </div>
                        </div>

                            <%-- Carousel --%>
                        <div id="carousel" class="carousel slide" data-ride="carousel">
                            <ol class="carousel-indicators">
                                <li data-target="#carousel" data-slide-to="0" class="active"></li>
                                <c:forEach begin="1" end="${roomType.amountOfImg}" varStatus="loop">
                                    <li data-target="#carousel" data-slide-to="${loop.index}"></li>
                                </c:forEach>
                            </ol>
                            <div class="carousel-inner">
                                <div class="carousel-item active">
                                    <img class="d-block w-100" src="${roomType.imgPaths[0].path}" alt="1">
                                </div>
                                <c:forEach begin="1" end="${roomType.amountOfImg}" varStatus="loop">
                                    <div class="carousel-item">
                                        <fmt:parseNumber var="loopNum" value="${loop.count}" integerOnly="true"/>
                                        <img class="d-block w-100" src="${roomType.imgPaths[loopNum].path}" alt="${loopNum}">
                                    </div>
                                </c:forEach>
                            </div>
                            <a class="carousel-control-prev" href="#carousel" role="button" data-slide="prev">
                                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                <span class="sr-only">Previous</span>
                            </a>
                            <a class="carousel-control-next" href="#carousel" role="button" data-slide="next">
                                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                <span class="sr-only">Next</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </c:otherwise>
    </c:choose>
    <c:remove var="roomType"></c:remove>
</t:wrapper>
