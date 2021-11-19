package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.RoomService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.CommandConstant.*;

public class ProfileCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        if (req.getSession().getAttribute(ATTRIBUTE_ID) == null) {
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_ERROR, "You have to login first");
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }

        List<Reservation> reservations = ReservationService.getByUser((Long) req.getSession().getAttribute(ATTRIBUTE_ID));
        List<Request> requests = RequestService.getByUserId((Long) req.getSession().getAttribute(ATTRIBUTE_ID));

        for (Reservation r : reservations){
            Optional<Room> room = RoomService.getById(r.getRoomId());
            room.ifPresent(value -> r.setRoomNumber(value.getRoomNumber()));
        }
        for (Request r : requests){
            Optional<Room> room = RoomService.getById(r.getRoomId());
            room.ifPresent(value -> r.setRoomNumber(value.getRoomNumber()));
        }
        req.getSession().setAttribute(ATTRIBUTE_USER_RESERVATIONS_LIST, reservations);
        req.getSession().setAttribute(ATTRIBUTE_USER_REQUEST_LIST, requests);

        return new Response(Response.Direction.Forward, PROFILE_JSP);
    }

    @Override
    public String getCommand() {
        return PROFILE;
    }
}
