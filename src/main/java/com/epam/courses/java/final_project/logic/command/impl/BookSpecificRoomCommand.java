package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;

import static com.epam.courses.java.final_project.util.CommandConstant.*;
import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class BookSpecificRoomCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        ReservationService.createByRoom(Long.valueOf(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
                Long.valueOf(req.getParameter(PARAM_ROOM_ID)), Date.valueOf(req.getSession().getAttribute(ATTRIBUTE_FROM).toString()),
                Date.valueOf(req.getSession().getAttribute(ATTRIBUTE_TO).toString()));
        return new Response(Response.Direction.Redirect, PROFILE_ACT);
    }

    @Override
    public String getCommand() {
        return BOOK_SPECIFIC_ROOM;
    }
}
