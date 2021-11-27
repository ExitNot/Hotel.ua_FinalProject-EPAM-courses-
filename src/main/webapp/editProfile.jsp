<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${not empty param.language ? param.language : language}" scope="session"/>
<fmt:setBundle basename="web-text"/>

<c:if test="${empty id}">
    <c:redirect url="profile.act"></c:redirect>
</c:if>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center">
                <h5 class="mt-3 mr-auto"><fmt:message key="edit.button.editProfile"/></h5>
<%--                <form action="deleteUser.act" method="post" id="delete_user">--%>
<%--                    <a href="#" onclick="document.getElementById('delete_user').submit();" style="color: black">--%>
<%--                        <i class="fas fa-user-slash fa-lg float-right"></i>--%>
<%--                    </a>--%>
<%--                </form>--%>
            </div>
            <div class="card-body pb-0">
                <form action="userUpdate.act" method="post" id="user_update_form" class="mb-0">
                    <div class="row mb-2">
                        <div class="col-2">
                            <fmt:message key="acc.label.name"/>:
                        </div>
                        <div class="col">
                            <input class="form-control" name="name" type="text" value="${user.name}" required>
                        </div>
                        <div class="col">
                            <input class="form-control" name ="surname" type="text" value="${user.surname}" required>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-2">
                            <fmt:message key="acc.label.email"/>:
                        </div>
                        <div class="col">
                            <input class="form-control" type="email" name="email" value="${user.email}" required>
                        </div>
                    </div>
                    <div class="row mb-3 pb-2">
                        <div class="col-2">
                            <fmt:message key="acc.label.phoneNum"/>:
                        </div>
                        <div class="col">
                            <input class="form-control" type="text" name="phoneNumber" value="${user.phoneNumber}" required>
                        </div>
                    </div>
                    <div class="row mt-3" style="background-color: mediumslateblue; margin-left: -20px; margin-right: -20px">
                        <div class="col">
                            <a href="#" class="btn w-100" style="color: white">  <%-- todo finsh pwd change --%>
                                <fmt:message key="edit.button.changePwd"/>
                            </a>
                        </div>
                        <div class="col">
                            <a class="btn w-100" style="color: white"
                               onclick="document.getElementById('user_update_form').submit()">
                                <fmt:message key="edit.button.saveChanges"/>
                            </a>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

<%-- Password change modal --%>
    <div class="modal fade" id="pwdChangeModal" tabindex="-1" role="dialog" aria-labelledby="modalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-md" role="form">
            <div class="modal-content">
                <div class="modal-body justify-content-center mt-3">
                    <form action="pwdChange.act" ></form>
                </div>
            </div>
        </div>
    </div>
</t:wrapper>
