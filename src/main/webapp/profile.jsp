<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="profile.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center py-2">
                <h5 class="mt-3 mr-auto text-capitalize"><fmt:message key="label.profile"/></h5>
                <form action="deleteUser.act" method="post" id="delete_user">
                    <a href="#" style="color: black"
                       onclick="document.getElementById('delete_user').submit();"
                       data-bs-toggle="tooltip" data-bs-placement="bottom" title="Delete user">
                        <i class="fas fa-user-slash fa-lg float-right"></i>
                    </a>
                </form>
            </div>
            <div class="card-body pb-0">
                <div class="row mb-2">
                    <div class="col-2">
                        <fmt:message key="acc.label.name"/>:
                    </div>
                    <div class="col">
                        <input class="form-control" type="text" placeholder="${user.name}" readonly>
                    </div>
                    <div class="col">
                        <input class="form-control" type="text" placeholder="${user.surname}" readonly>
                    </div>
                </div>
                <div class="row mb-2">
                    <div class="col-2">
                        <fmt:message key="acc.label.email"/>:
                    </div>
                    <div class="col">
                        <input class="form-control" type="email" placeholder="${user.email}" readonly>
                    </div>
                </div>
                <div class="row mb-3 pb-2">
                    <div class="col-2">
                        <fmt:message key="acc.label.phoneNum"/>:
                    </div>
                    <div class="col">
                        <input class="form-control" type="text" placeholder="${user.phoneNumber}" readonly>
                    </div>
                </div>
                <div class="row mt-3" style="background-color: mediumslateblue; margin-left: -20px; margin-right: -20px">
                    <div class="col">
                        <a href="logout.act" class="btn w-100" style="color: white">Logout</a>
                    </div>
                    <div class="col">
                        <a href="./editProfile.jsp" class="btn w-100" style="color: white"><fmt:message key="edit.button.editProfile"/></a>
                    </div>
                </div>
            </div>
        </div>
    </div>

</t:wrapper>
