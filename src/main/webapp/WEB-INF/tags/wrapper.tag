<%@ tag description="Base page structure wrapper" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ attribute name="onload" required="false" %>--%>
<html>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/core.css" type="text/css">
    <head>
        <title>Hotel</title>
        <%-- jQuery --%>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.10.3/jquery-ui.js"></script>
        <link href = "http://ajax.googleapis.com/ajax/libs/jqueryui/1.8.7/themes/black-tie/jquery-ui.css"
              rel = "stylesheet">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
              integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
              crossorigin="anonymous">
        <!-- Bootstrap CSS -->
<%--        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">--%>
        <link href="${contextPath}/bootstarp/bootstrap.min.css" rel="stylesheet">

    </head>
    <nav class="navbar navbar-light" style="background-color: goldenrod;">
<%--        <button class="navbar-toggler" type="button" data-toggle="collapse.show"--%>
<%--                data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01"--%>
<%--                aria-expanded="false" aria-label="Toggle navigation">--%>
<%--            <span class="navbar-toggler-icon"></span>--%>
<%--        </button>--%>
        <div class="collapse.show navbar-collapse" id="navbar">
            <a class="navbar-brand" href="index.act">Hotel</a>
            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                <li class="nav-item active">
                    <a class="nav-link" id="nav_index" href="index.act">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="nav_request" href="./request.jsp">Make a reservation</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" id="nav_available_rooms" href="availableRooms.act">Available rooms</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Contacts</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="#">Info</a>
                </li>
<%--                <li class="nav-item">--%>
<%--                    <a class="nav-link disabled" href="#">Disabled</a>--%>
<%--                </li>--%>
            </ul>
            <form class="form-inline my-2 my-lg-0">
                <input class="form-control mr-sm-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>
        </div>
    </nav>
    <body class="body">
        <jsp:doBody/>
    </body>
</html>