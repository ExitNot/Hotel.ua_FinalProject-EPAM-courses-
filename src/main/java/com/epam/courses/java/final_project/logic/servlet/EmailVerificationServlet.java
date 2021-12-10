package com.epam.courses.java.final_project.logic.servlet;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

/**
 * Servlet for email verification. Activated when user follow the link from email for verification process.
 * Verification using not only email address, but also special generated code.
 * Verification that equal to null - means already verified account.
 *
 * @author Kostiantyn Kolchenko
 **/
public class EmailVerificationServlet extends HttpServlet {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String email = req.getParameterValues("email")[0];
        String verificationCode = req.getParameterValues("code")[0];

        try {
            Optional<User> oUser = UserService.getByEmail(email);
            if (oUser.isPresent()){
                User u = oUser.get();
                if (u.getVerification() == null){
                    req.getSession().setAttribute(ATTRIBUTE_INDEX_NOTIFICATION, "Email is already verified");
                    resp.sendRedirect(INDEX_ACT);
                    return;
                }
                if (u.getVerification().equals(verificationCode)){  // verification done
                    u.setVerification(null);
                    UserService.update(u);
                    req.getSession().setAttribute(ATTRIBUTE_ID, u.getId());
                    req.getSession().setAttribute(ATTRIBUTE_EMAIL, u.getEmail());
                    req.getSession().setAttribute(ATTRIBUTE_ROLE, u.getRole().name());
                    req.getSession().setAttribute(ATTRIBUTE_INDEX_NOTIFICATION, "We have verified your email");
                    resp.sendRedirect(INDEX_ACT);
                    return;
                } else {
                    log.error("Error in email validation, wrong verification code");
                    req.getSession().setAttribute(ATTRIBUTE_EX, "Wrong verification code");
                }
            } else {
                log.error("Error in email validation, user was not found");
                req.getSession().setAttribute(ATTRIBUTE_EX, "User was not found");
            }
            resp.sendRedirect(ERROR_JSP);
        } catch (JDBCException e) {
            log.error("Error in email validation", e);
        }
    }
}
