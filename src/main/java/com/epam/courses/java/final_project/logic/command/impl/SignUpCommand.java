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

import static com.epam.courses.java.final_project.util.CommandConstant.*;
import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class SignUpCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
//        todo check everything not null

        if (UserService.getByEmail(req.getParameter(PARAM_EMAIL)).isPresent()){
            req.getSession().setAttribute(ATTRIBUTE_SIGN_UP_EX, "User already exist");
            return new Response(Response.Direction.Forward, SIGN_UP_JSP);
        }

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
                req.getParameter(PARAM_PHONE_NUM));

        log.trace(user.toString());
        long id = UserService.create(user);
//        todo send email of confirmation

        req.getSession().setAttribute(ATTRIBUTE_ID, id);
        req.getSession().setAttribute(ATTRIBUTE_EMAIL, user.getEmail());
        req.getSession().setAttribute(ATTRIBUTE_ROLE, user.getRole().name());
        return new Response(Response.Direction.Redirect, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return SIGN_UP;
    }
}
