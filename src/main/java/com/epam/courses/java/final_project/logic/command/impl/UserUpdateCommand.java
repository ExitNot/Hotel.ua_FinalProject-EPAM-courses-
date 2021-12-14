package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.PasswordCryptoPbkdf2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class UserUpdateCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        Optional<User> oUser = UserService.getById(Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()));
        String lang = req.getSession().getAttribute(ATTRIBUTE_LANG).toString();

        if (oUser.isPresent()){
            User u = oUser.get();
            if (req.getParameter(PARAM_PWD) != null) {
                String pwd = req.getParameter(PARAM_PWD);
                String newPwd = req.getParameter(PARAM_NEW_PWD);
                if (!PasswordCryptoPbkdf2.validatePwd(req.getParameter(PARAM_PWD), u.getPassword())) {
                    if (lang.equals("ru"))
                        req.getSession().setAttribute(ATTRIBUTE_USER_UPDATE_EX,
                                "\u041D\u0435\u0432\u0435\u0440\u043D\u044B\u0439 " +
                                      "\u043F\u0430\u0440\u043E\u043B\u044C(\u043F\u0440\u043E\u0448\u043B\u044B\u0439)");
                    else
                        req.getSession().setAttribute(ATTRIBUTE_USER_UPDATE_EX, "Old password is incorrect!");
                    return new Response(Response.Direction.Forward, EDIT_PROFILE_JSP);
                }
                if (!newPwd.equals(req.getParameter(PARAM_NEW_PWD + "Confirmation"))) {
                    if (lang.equals("ru"))
                        req.getSession().setAttribute(ATTRIBUTE_USER_UPDATE_EX,
                                "\u041F\u0430\u0440\u043E\u043B\u0438 \u043D\u0435 " +
                                      "\u0441\u043E\u0432\u043F\u0430\u0434\u0430\u044E\u0442");
                    else
                        req.getSession().setAttribute(ATTRIBUTE_USER_UPDATE_EX, "Passwords were different");
                    return new Response(Response.Direction.Forward, EDIT_PROFILE_JSP);
                }
                u.setPassword(PasswordCryptoPbkdf2.hashPwd(newPwd));
            }
            if (req.getParameter(PARAM_NAME) != null) {
                u.setName(req.getParameter(PARAM_NAME));
            }
            if (req.getParameter(PARAM_SURNAME) != null) {
                u.setSurname(req.getParameter(PARAM_SURNAME));
            }
            if (req.getParameter(PARAM_EMAIL) != null) {
                u.setEmail(req.getParameter(PARAM_EMAIL));
            }
            if (req.getParameter(PARAM_PHONE_NUM) != null) {
                u.setPhoneNumber(req.getParameter(PARAM_PHONE_NUM));
            }
            UserService.update(u);
        }

        return new Response(Response.Direction.Redirect, PROFILE_ACT);
    }

    @Override
    public String getCommand() {
        return USER_UPDATE;
    }
}
