package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.MailManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class BookSpecificRoomCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        Request request = new Request(Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
                Long.parseLong(req.getParameter(PARAM_ROOM_ID)),
                Date.valueOf(req.getSession().getAttribute(ATTRIBUTE_FROM).toString()),
                Date.valueOf(req.getSession().getAttribute(ATTRIBUTE_TO).toString()),
                0, 0, Request.Status.Payment, 0);

        RequestService.create(request);

        Optional<User> oUser = UserService.getById(Long.parseLong(req.getParameter(ATTRIBUTE_ID)));
        oUser.ifPresent(user -> MailManager.getInstance().sendEmail(
                user.getEmail(), MailManager.creatingRequestMailTemplate(user.getName(), user.getSurname(),
                        req.getSession().getAttribute(ATTRIBUTE_LANG).toString()))
        );
        return new Response(Response.Direction.Redirect, PROFILE_ACT);
    }

    @Override
    public String getCommand() {
        return BOOK_SPECIFIC_ROOM;
    }
}
