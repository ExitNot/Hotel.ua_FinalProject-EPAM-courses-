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
        String lang = req.getSession().getAttribute(ATTRIBUTE_LANG).toString();

        if (UserService.getByEmail(req.getParameter(PARAM_EMAIL)).isPresent()){
            if (lang.equals("ru"))
                req.getSession().setAttribute(ATTRIBUTE_SIGN_UP_EX, "\u041F\u043E\u043B\u044C\u0437\u043E\u0432\u0430\u0442\u0435\u043B\u044C \u0443\u0436\u0435 \u0441\u0443\u0449\u0435\u0441\u0442\u0432\u0443\u0435\u0442");
            else
                req.getSession().setAttribute(ATTRIBUTE_SIGN_UP_EX, "User already exist");
            return new Response(Response.Direction.Forward, SIGN_UP_JSP);
        }

        String verification = String.valueOf(new SecureRandom().nextInt(100000));
        String pwd = req.getParameter(PARAM_PWD);
        if (!pwd.equals(req.getParameter(PARAM_PWD + "Confirmation"))){
            if (lang.equals("ru"))
                req.getSession().setAttribute(ATTRIBUTE_SIGN_UP_EX, "\u041F\u0430\u0440\u043E\u043B\u0438 \u043D\u0435 \u0441\u043E\u0432\u043F\u0430\u0434\u0430\u044E\u0442");
            else
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
        if (lang.equals("ru"))
            req.getSession().setAttribute(ATTRIBUTE_INDEX_NOTIFICATION, "\u041F\u043E\u0436\u0430\u043B\u0443\u0439\u0441\u0442\u0430 \u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u0435 \u0441\u0432\u043E\u0439 email");
        else
            req.getSession().setAttribute(ATTRIBUTE_INDEX_NOTIFICATION, "Please verify your email");
        return new Response(Response.Direction.Redirect, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return SIGN_UP;
    }
}
