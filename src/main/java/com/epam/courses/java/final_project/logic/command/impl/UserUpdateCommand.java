package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.PasswordCryptoPbkdf2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.epam.courses.java.final_project.util.CommandConstant.*;

public class UserUpdateCommand implements Command {
    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        Optional<User> oUser = UserService.getById(Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()));

        if (oUser.isPresent()){
            if (req.getParameter(PARAM_PWD) != null) {
                String pwd = req.getParameter(PARAM_NEW_PWD);
                if (!PasswordCryptoPbkdf2.validatePwd(req.getParameter(PARAM_PWD), oUser.get().getPassword())) {
                    req.getSession().setAttribute(ATTRIBUTE_USER_UPDATE_EX, "Old password is incorrect!");
                    return new Response(Response.Direction.Forward, EDIT_PROFILE_JSP);
                }
                if (!pwd.equals(req.getParameter(PARAM_NEW_PWD + "Confirmation"))) {
                    req.getSession().setAttribute(ATTRIBUTE_USER_UPDATE_EX, "Passwords were different");
                    return new Response(Response.Direction.Forward, EDIT_PROFILE_JSP);
                }
                oUser.get().setPassword(PasswordCryptoPbkdf2.hashPwd(pwd));
            }
            if (req.getParameter(PARAM_NAME) != null) {
                oUser.get().setName(req.getParameter(PARAM_NAME));
            }
            if (req.getParameter(PARAM_SURNAME) != null) {
                oUser.get().setSurname(req.getParameter(PARAM_SURNAME));
            }
            if (req.getParameter(PARAM_EMAIL) != null) {
                oUser.get().setEmail(req.getParameter(PARAM_EMAIL));
            }
            if (req.getParameter(PARAM_PHONE_NUM) != null) {
                oUser.get().setPhoneNumber(req.getParameter(PARAM_PHONE_NUM));
            }
            UserService.update(oUser.get());
        }

        return new Response(Response.Direction.Redirect, PROFILE_ACT);
    }

    @Override
    public String getCommand() {
        return USER_UPDATE;
    }
}
