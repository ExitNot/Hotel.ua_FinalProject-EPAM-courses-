package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.MailManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class PaymentCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        Optional<Request> request = RequestService.getById(Long.parseLong(req.getParameter(PARAM_REQUEST_ID)));
        Date from;
        Date to;
        if (request.isPresent()) {
            Request r = request.get();
            from = r.getFrom();
            to = r.getTo();
            RequestService.delete(r.getId());
            ReservationService.createByRequest(r);

            Optional<User> oUser = UserService.getById((long) req.getSession().getAttribute(ATTRIBUTE_ID));
            oUser.ifPresent(user -> MailManager.getInstance().sendEmail(
                    user.getEmail(), MailManager.paymentMailTemplate(user.getName(), user.getSurname(), from, to)
            ));
        }
        return new Response(Response.Direction.Redirect, MY_RESERVATIONS_ACT);
    }

    @Override
    public String getCommand() {
        return PAYMENT;
    }
}
