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
    </head>
    <body>
        <jsp:doBody/>
    </body>
</html>