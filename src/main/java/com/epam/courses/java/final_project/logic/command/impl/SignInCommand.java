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

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_INFO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignInCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_INFO);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        String errMsg = "Incorrect email or password";
        String lang = req.getSession().getAttribute(ATTRIBUTE_LANG).toString();
        if (lang.equals("ru"))
            errMsg = "\u041D\u0435\u0432\u0435\u0440\u043D\u044B\u0439 email " +
                    "\u0438\u043B\u0438 \u043F\u0430\u0440\u043E\u043B\u044C";
        String email = req.getParameter(PARAM_EMAIL);
        String password = req.getParameter(PARAM_PWD);

        if (req.getParameter(PARAM_ID) != null) {
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }

        Optional<User> user = UserService.getByEmail(email);
        if (user.isEmpty()) {
            req.getSession().setAttribute(ATTRIBUTE_EMAIL, email);
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_EX, errMsg);
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        } else if (!PasswordCryptoPbkdf2.validatePwd(password, user.get().getPassword())) {
            req.getSession().setAttribute(ATTRIBUTE_EMAIL, email);
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_EX, errMsg);
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        } else if (user.get().getVerification() != null) {
            req.getSession().setAttribute(ATTRIBUTE_EMAIL, email);
            if (lang.equals("ru"))
                req.getSession().setAttribute(ATTRIBUTE_LOGIN_EX, "\u0414\u043B\u044F \u0432\u0445\u043E\u0434\u0430," +
                        " \u043D\u0435\u043E\u0431\u0445\u043E\u0434\u0438\u043C\u043E" +
                        " \u043F\u043E\u0434\u0442\u0432\u0435\u0440\u0434\u0438\u0442\u044C \u0432\u0430\u0448 email");
            else
                req.getSession().setAttribute(ATTRIBUTE_LOGIN_EX, "Please verify your email first");
            MailManager.getInstance().sendEmailVerification(email, user.get().getName(),
                    user.get().getSurname(), user.get().getVerification());
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }

        req.getSession().setAttribute(ATTRIBUTE_ID, user.get().getId());
        req.getSession().setAttribute(ATTRIBUTE_USER, user.get());
        req.getSession().setAttribute(ATTRIBUTE_ROLE, user.get().getRole().name());
        return new Response(Response.Direction.Redirect, INDEX_ACT);
    }

    @Override
    public String getCommand() {
        return SIGN_IN;
    }
}
