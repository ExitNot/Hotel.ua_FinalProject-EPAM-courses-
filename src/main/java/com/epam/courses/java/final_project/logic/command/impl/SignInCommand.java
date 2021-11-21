package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.PasswordCryptoPbkdf2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.CommandConstant.*;
import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;
import static com.epam.courses.java.final_project.util.Constant.PARAM_ID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

public class SignInCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        String errorMsg = "Incorrect email or password";
        String email = req.getParameter(PARAM_EMAIL);
        String password = req.getParameter(PARAM_PWD);

        if (req.getParameter(PARAM_ID) != null){
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }

        Optional<User> user = UserService.findByEmail(email);
        if (user.isEmpty()){
            req.getSession().setAttribute(ATTRIBUTE_EMAIL, email);
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_ERROR, errorMsg);
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        } else if (!PasswordCryptoPbkdf2.validatePwd(password, user.get().getPassword())){
            req.getSession().setAttribute(ATTRIBUTE_EMAIL, email);
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_ERROR, errorMsg);
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }

        req.getSession().setAttribute(ATTRIBUTE_ID, user.get().getId());
        req.getSession().setAttribute(ATTRIBUTE_USER, user.get().getEmail());
        req.getSession().setAttribute(ATTRIBUTE_ROLE, user.get().getRole().name());
        return new Response(Response.Direction.Redirect, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return SIGN_IN;
    }
}
