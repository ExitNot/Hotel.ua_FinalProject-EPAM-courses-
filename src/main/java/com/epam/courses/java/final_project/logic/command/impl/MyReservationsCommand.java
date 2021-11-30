package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.RoomTypeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class MyReservationsCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Reservation> reservations = ReservationService.getByUser((Long) req.getSession().getAttribute(ATTRIBUTE_ID));

        for (Reservation r : reservations){
            Optional<Room> room = RoomService.getById(r.getRoomId());
            if (room.isPresent()){
                Optional<RoomType> rt = RoomTypeService.getById(room.get().getRoomTypeId());
                r.setRoomNumber(room.get().getRoomNumber());
                rt.ifPresent(r::setRoomType);
            }
        }

        req.getSession().setAttribute(ATTRIBUTE_RESERVATIONS_LIST, reservations);
        return new Response(Response.Direction.Forward, MY_RESERVATIONS_JSP);
    }

    @Override
    public String getCommand() {
        return MY_RESERVATIONS;
    }
}
