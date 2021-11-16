package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.service.ReservationService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.courses.java.final_project.util.CommandConstant.*;

public class ProfileCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Reservation> list = ReservationService.getByUser((Long) req.getSession().getAttribute(ATTRIBUTE_ID));
//        todo replace with REQUEST!!!
        req.getSession().setAttribute(ATTRIBUTE_USER_REQUEST_LIST, list);
        return new Response(Response.Direction.Forward, PROFILE_JSP);
    }

    @Override
    public String getCommand() {
        return PROFILE;
    }
}
