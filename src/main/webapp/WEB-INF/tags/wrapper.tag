<%@ tag description="Base page structure wrapper" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<html lang="${language}">
    <head>
        <title>Hotel ${language}</title>
        <%-- jQuery --%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>
        <link href = "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.7/themes/black-tie/jquery-ui.css"
              rel = "stylesheet">
        <%-- Bootstrap --%>
        <link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css" rel="stylesheet">
        <script type="text/javascript" src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/core.css" type="text/css">
    </head>

    <%-- Fontawesome --%>
    <script src="https://kit.fontawesome.com/b9b6a4bb7d.js" crossorigin="anonymous"></script>

    <nav class="navbar navbar-expand-lg navbar-dark" style="background-color: mediumslateblue; border-bottom: solid black">
        <div class="collapse.show navbar-collapse" id="navbar">
            <a class="navbar-brand" href="index.act" style="font-size: 30px">Hotel</a>
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" href="index.act"><fmt:message key="nav.label.home"/></a>
                </li>
                <c:choose>
                    <c:when test="${role == 'Customer'}">
                        <li class="nav-item">
                            <a class="nav-link" href="./request.jsp">
                                <fmt:message key="nav.label.makeAReservation"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="./availableRooms.act">
                                <fmt:message key="nav.label.availableRooms"/>
                            </a>
                        </li>
                    </c:when>
                    <c:when test="${role == 'Manager'}">
                        <li class="nav-item">
                            <a class="nav-link" href="./requestsList.act"><fmt:message key="label.requests"/></a>
                        </li>
                    </c:when>
                </c:choose>
                <li class="nav-item">
                    <a class="nav-link" href="#"><fmt:message key="nav.label.contacts"/></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#"><fmt:message key="nav.label.info"/></a>
                </li>
            </ul>
            <form>
                <select class="form-select mr-3 mt-3 font-weight-bold" name="language"  <%-- Language changer --%>
                        onchange="submit()" aria-label="Default select example"
                        style="appearance: none; background-color: mediumslateblue; color: white">
                    <option value="en" ${language == 'en' ? 'selected' : ''}>EN</option>
                    <option value="ru" ${language == 'ru' ? 'selected' : ''}>RU</option>
                </select>
            </form>
            <c:choose>
                <c:when test="${empty id}">
                    <a href="#" id="sign_in_btn" class="button" data-toggle="modal" data-target="#signInModal">Sign in</a>
                    <a href="./signUp.jsp" class="button">Sign up</a>
                </c:when>
                <c:when test="${role == 'Customer'}">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <div class="btn dropdown show">  <%-- profile icon --%>
                                <a href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <span style="color: black">
                                        <i class="fas fa-user-circle fa-2x"></i>
                                    </span>
                                </a>

                                <div class="dropdown-menu dropdown-menu-right" data-offset="2" aria-labelledby="dropdownMenuLink">
                                    <a class="dropdown-item" href="profile.act">
                                        <fmt:message key="label.profile"/></a>
                                    <a class="dropdown-item" href="myReservations.act">
                                        <fmt:message key="label.my"/> <fmt:message key="label.reservations"/></a>
                                    <a class="dropdown-item" href="myRequests.act">
                                        <fmt:message key="label.my"/> <fmt:message key="label.requests"/></a>
                                </div>
                            </div>
                        </li>
                    </ul>
                </c:when>
                <c:when test="${role == 'Manager'}">
                    <ul class="navbar-nav">
                        <li class="nav-item dropdown">
                            <div class="btn dropdown show">  <%-- profile icon --%>
                                <a href="profile.act" role="button">
                                    <span style="color: black">
                                        <i class="fas fa-user-circle fa-2x"></i>
                                    </span>
                                </a>
                            </div>
                        </li>
                    </ul>
                </c:when>
            </c:choose>

        </div>
    </nav>

    <!-- Sign in modal -->
    <div class="modal fade" id="signInModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-body">
                    <form class="px-3 pt-3 mb-0" action="signIn.act" method="get" id="signInForm">
                        <c:if test="${not empty loginEx}">
                            <b class="float-right" style="color:red; font-size: 12px;">${loginEx}</b>
                            <c:remove var="loginEx"/>
                        </c:if>
                        <input type="hidden" name="command" value="signIn"/>
                        <div class="form-group mb-0">
                            <c:choose>
                                <c:when test="${not empty email}">
                                    <input class="form-control" name="email" value="${email}"/><br/>
                                </c:when >
                                <c:otherwise>
                                    <input class="form-control" name="email" placeholder="Email"/><br/>
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="form-group">
                            <input type="password" class="form-control" id="pwd" name="pwd" placeholder="Password">
                        </div>
                        <div class="form-check pl-1">
                            <label class="form-check-label ml-4">
                                <input type="checkbox" class="form-check-input col-md-1 w-25" id="dropdownCheck">
                                <a class="col-md-2"><fmt:message key="acc.label.rememberMe"/></a>
                            </label>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <a class="col-l-5" href="#"><fmt:message key="acc.link.forgotPwd"/>?</a>
                    <button type="submit" form="signInForm" class="btn btn-primary col-5">Sign in</button>
                </div>
            </div>
        </div>
    </div>

    <body class="body">
        <jsp:doBody/>
    </body>
</html>