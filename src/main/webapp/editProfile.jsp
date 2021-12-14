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

<script>

    window.onload = function () {
        var current = document.getElementsByClassName("active");
        current[0].className = current[0].className.replace(" active", "");

        <c:if test="${not empty userUpdateEx}">
        document.getElementById("error_modal_btn").click();
        </c:if>
    }

</script>

<t:wrapper>
    <div class="container px-0 pt-4 d-flex justify-content-center" style="border: #6610f2">
        <div class="card col-10 px-0">
            <div class="card-header d-flex justify-content-center">
                <h5 class="mt-3 mr-auto"><fmt:message key="edit.button.editProfile"/></h5>
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
                            <a href="#" class="btn w-100" data-toggle="modal" data-target="#pwdChangeModal"
                               style="color: white">
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
                <div class="modal-body justify-content-center mt-3 py-1">
                    <form action="pwdUpdate.act" method="post" id="pwdUpdate">
                        <div class="form-row">
                            <label class="col">
                                <fmt:message key="edit.label.oldPwd"/>:
                                <input class="form-control" type="password" name="pwd"
                                       placeholder="<fmt:message key="edit.label.oldPwd"/>" required><br/>
                            </label>
                        </div>
                        <div class="form-row">
                            <label class="col">
                                <fmt:message key="edit.label.newPwd"/>:
                                <input class="form-control" type="password" name="newPwd"
                                       placeholder="<fmt:message key="edit.label.newPwd"/>" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" required><br/>
                            </label>
                        </div>
                        <div class="form-row">
                            <label class="col">
                                <fmt:message key="edit.label.newPwdConfirmation"/>:
                                <input class="form-control" type="password" name="newPwdConfirmation" placeholder="<fmt:message key="edit.label.newPwdConfirmation"/>" required><br/>
                            </label>
                        </div>
                    </form>
                </div>
                <div class="modal-footer mb-0 py-0"
                     style="height: 40px; background-color: mediumslateblue; border-top: solid black;">
                    <input class="btn-link border-0" type="submit" form="pwdUpdate" style="color: white"/>
                </div>
            </div>
        </div>
    </div>

    <a href="#" type="hidden" id="error_modal_btn" data-toggle="modal" data-target="#errorModal"></a>

    <!-- Error modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1" style="color: red"><fmt:message key="label.error"/></h5>
                </div>
                <div class="modal-body justify-content-center">
                        ${userUpdateEx}
                </div>
            </div>
        </div>
    </div>
</t:wrapper>
