package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.MailManager;
import com.epam.courses.java.final_project.util.PasswordCryptoPbkdf2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.security.SecureRandom;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;
import static com.epam.courses.java.final_project.util.constant.Constant.PARAM_VERIFICATION;

public class SignUpCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        if (UserService.getByEmail(req.getParameter(PARAM_EMAIL)).isPresent()){
            req.getSession().setAttribute(ATTRIBUTE_SIGN_UP_EX, "User already exist");
            return new Response(Response.Direction.Forward, SIGN_UP_JSP);
        }

        String verification = String.valueOf(new SecureRandom().nextInt(100000));
        String pwd = req.getParameter(PARAM_PWD);
        if (!pwd.equals(req.getParameter(PARAM_PWD + "Confirmation"))){
            req.getSession().setAttribute(ATTRIBUTE_SIGN_UP_EX, "Passwords were different");
            return new Response(Response.Direction.Forward, SIGN_UP_JSP);
        }
        pwd = PasswordCryptoPbkdf2.hashPwd(pwd);

        User user = new User(
                req.getParameter(PARAM_EMAIL),  pwd,
                req.getParameter(PARAM_NAME),
                req.getParameter(PARAM_SURNAME),
                req.getParameter(PARAM_PHONE_NUM),
                verification);

        long id = UserService.create(user);

        MailManager.getInstance().sendEmailVerification(user.getEmail(), user.getName(),
                user.getSurname(), user.getVerification());
        req.getSession().setAttribute("indexNotification", "Please verify your email");
        return new Response(Response.Direction.Redirect, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return SIGN_UP;
    }
}
