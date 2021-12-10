<%-- Created by Kostiantyn Kolchenko(@ExitNot) --%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<c:if test="${not empty role}">
    <c:redirect url="index.jsp"></c:redirect>
</c:if>

<script>
    window.onload = function () {
        <c:if test="${not empty signUpEx}">
            document.getElementById("error_modal_btn").click();
        </c:if>
    }
</script>

<t:wrapper>
    <div class="container">
        <div class="d-flex justify-content-center">
            <h5 class="mt-3">Sign up</h5>
        </div>
        <hr/>
        <form action="signUp.act" method="post">
            <input type="hidden" name="command" value="signUp">
            <div class="form-row">
                <div class="col-md-6">
                    <input class="form-control" name="name" placeholder="First name" required>
                </div>
                <div class="col-md-6">
                    <input class="form-control" name="surname" placeholder="Second name" required><br/>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md">
                    <input class="form-control" type="email" name="email" placeholder="Email" required>
                </div>
            </div>
            <small class="form-text text-muted">
                Password must be 8-15 Alphanumeric characters, at least one letter and one number
            </small>
            <div class="form-row">
                <div class="col-md-6">
                    <input class="form-control" type="password" name="pwd"
                           id="signUpPwd" placeholder="Password" pattern="^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$" required><br/>
                </div>
                <div class="col-md-6">
                    <input class="form-control" type="password" name="pwdConfirmation" placeholder="Confirm password" required><br/>
                </div>
            </div>
            <div class="form-row">
                <div class="col-md">
                    <input class="form-control" name="phoneNumber" placeholder="Phone number" required><br/>
                </div>
            </div>
            <input type="submit" name="signUpBtn" value="Sign Up" class="button float-right">
        </form>
    </div>

    <a href="#" type="hidden" id="error_modal_btn" data-toggle="modal" data-target="#errorModal"></a>

    <!-- Error modal -->
    <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="false">
        <div class="modal-dialog modal-dialog-centered modal-sm" role="form">
            <div class="modal-content">
                <div class="modal-header d-flex justify-content-center pb-0">
                    <h5 class="mt-1" style="color: red">Error</h5>
                </div>
                <div class="modal-body justify-content-center">
                    ${signUpEx}
<%--                    <c:remove var="signUpEx"/>--%>
                </div>
            </div>
        </div>
    </div>
</t:wrapper>
