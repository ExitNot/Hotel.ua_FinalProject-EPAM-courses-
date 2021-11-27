package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class ProfileCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        Optional<User> oUser = UserService.getById((Long) req.getSession().getAttribute(ATTRIBUTE_ID));
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

        oUser.ifPresent(user -> req.getSession().setAttribute(ATTRIBUTE_USER, user));
        req.getSession().setAttribute(ATTRIBUTE_RESERVATIONS_LIST, reservations);
        req.getSession().setAttribute(ATTRIBUTE_REQUEST_LIST, requests);
        return new Response(Response.Direction.Redirect, PROFILE_JSP);
    }

    @Override
    public String getCommand() {
        return PROFILE;
    }
}
