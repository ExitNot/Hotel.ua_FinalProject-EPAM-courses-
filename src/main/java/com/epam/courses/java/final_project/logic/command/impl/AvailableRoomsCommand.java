package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.RoomTypeService;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;
import static com.epam.courses.java.final_project.util.constant.Constant.PARAM_FLOOR;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AvailableRoomsCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Reservation> occupiedRooms;
        List<Room> availableRooms;
        req.getSession().setAttribute("floor", 0);
        String from = req.getParameter(PARAM_FROM);
        String to = req.getParameter(PARAM_TO);

        if (req.getSession().getAttribute(ATTRIBUTE_ID) == null) {
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_EX, "You have to login first");
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }

        availableRooms = RoomService.getAll();
        if (from == null){
            return new Response(Response.Direction.Redirect, AVAILABLE_ROOMS_JSP);
        }

        from = Util.transformDate(from);
        to = Util.transformDate(to);
        if (Date.valueOf(from).after(Date.valueOf(to))){
            req.getSession().setAttribute(ATTRIBUTE_ROOMS_LIST_EX, "Incorrect dates");
            return new Response(Response.Direction.Forward, AVAILABLE_ROOMS_JSP);
        }
        req.getSession().setAttribute(ATTRIBUTE_FROM, from);
        req.getSession().setAttribute(ATTRIBUTE_TO, to);

        occupiedRooms = ReservationService.getByDate(
                Date.valueOf(from), Date.valueOf(to)
        );
        List<Long> occupiedRoomsId = occupiedRooms.stream()
                .map(Reservation::getRoomId).collect(Collectors.toList());
        for (Long i : occupiedRoomsId){
            availableRooms.removeIf(a -> i.equals(a.getId()));
        }

//        Filter/Sorting
        if (!req.getParameter(PARAM_FLOOR).equals("0")){
            log.trace("floor: " + req.getParameter(PARAM_FLOOR));
            req.getSession().setAttribute("floor", req.getParameter(PARAM_FLOOR));
            availableRooms = availableRooms.stream()
                    .filter(a -> a.getFloor() == Integer.parseInt(req.getParameter(PARAM_FLOOR)))
                    .collect(Collectors.toList());
        }

//        Insert RoomType
        for (Room r : availableRooms){
            RoomTypeService.getById(r.getRoomTypeId()).ifPresent(r::setRoomType);
        }


        req.getSession().setAttribute(ATTRIBUTE_FROM, from.substring(0, 10));
        req.getSession().setAttribute(ATTRIBUTE_TO, to.substring(0, 10));
        req.getSession().setAttribute(ATTRIBUTE_ROOMS_LIST, availableRooms);
        return new Response(Response.Direction.Forward, AVAILABLE_ROOMS_JSP);
    }

    @Override
    public String getCommand() {
        return AVAILABLE_ROOMS;
    }
}
