package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.ReservationService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.RoomTypeService;
import com.epam.courses.java.final_project.util.Util;
import com.epam.courses.java.final_project.util.constant.CommandConstant;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class AvailableRoomsCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Room> availableRooms;
        req.getSession().setAttribute("floor", 0);
        String from = req.getParameter(PARAM_FROM);
        String to = req.getParameter(PARAM_TO);
        String lang = req.getSession().getAttribute(ATTRIBUTE_LANG).toString();

        if (from == null){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            from = new Date(c.getTimeInMillis()).toString();
            c.add(Calendar.DAY_OF_YEAR, 1);
            to = new Date(c.getTimeInMillis()).toString();
        } else {
            req.getSession().setAttribute(ATTRIBUTE_FROM, from);
            req.getSession().setAttribute(ATTRIBUTE_TO, to);
            from = Util.transformDate(from);
            to = Util.transformDate(to);
        }


        if (Date.valueOf(from).after(Date.valueOf(to))){
            if (lang.equals("ru"))
                req.getSession().setAttribute(ATTRIBUTE_ROOMS_LIST_EX, "\u0414\u0430\u0442\u044B \u0432\u0432\u0435\u0434\u0435\u043D\u044B \u043D\u0435\u043F\u0440\u0430\u0432\u0438\u043B\u044C\u043D\u043E");
            else
                req.getSession().setAttribute(ATTRIBUTE_ROOMS_LIST_EX, "Incorrect dates");
            return new Response(Response.Direction.Forward, AVAILABLE_ROOMS_JSP);
        }

        availableRooms = getAvailableRooms(from, to);

//        Filtering
        if (req.getParameter(CommandConstant.PARAM_ROOM_TYPE_ID) != null){
            Optional<RoomType> roomType = RoomTypeService.getById(Long.parseLong(req.getParameter(PARAM_ROOM_TYPE_ID)));
            if (roomType.isPresent()){
                RoomType rt = roomType.get();
                req.getSession().setAttribute(ATTRIBUTE_ROOM_TYPE, rt);
                req.getSession().setAttribute(ATTRIBUTE_ROOM_CLASS, String.valueOf(rt.getRoomClass().getValue()));
                req.getSession().setAttribute(ATTRIBUTE_CAPACITY, rt.getCapacity());
                availableRooms = availableRooms.stream()
                        .filter(a -> a.getRoomTypeId() == rt.getId())
                        .collect(Collectors.toList());
            }
        }

        if (req.getParameter(PARAM_CAPACITY) != null && !req.getParameter(PARAM_CAPACITY).equals("0")){
            List<RoomType> list = RoomTypeService.getByCapacity(Integer.parseInt(req.getParameter(PARAM_CAPACITY)));
            List<Long> listId = list.stream()
                    .map(RoomType::getId).collect(Collectors.toList());
            availableRooms.removeIf(a -> !listId.contains(a.getRoomTypeId()));
            req.getSession().setAttribute(ATTRIBUTE_CAPACITY, req.getParameter(PARAM_CAPACITY));
        }

        if (req.getParameter(PARAM_FLOOR) != null && !req.getParameter(PARAM_FLOOR).equals("0")){
            req.getSession().setAttribute("floor", req.getParameter(PARAM_FLOOR));
            availableRooms = availableRooms.stream()
                    .filter(a -> a.getFloor() == Integer.parseInt(req.getParameter(PARAM_FLOOR)))
                    .collect(Collectors.toList());
            req.getSession().setAttribute(ATTRIBUTE_FLOOR, req.getParameter(PARAM_FLOOR));
        }


        for (Room r : availableRooms){  // Insert RoomType
            RoomTypeService.getById(r.getRoomTypeId()).ifPresent(r::setRoomType);
        }

        if (req.getParameter(PARAM_ROOM_CLASS) != null && !req.getParameter(PARAM_ROOM_CLASS).equals("0")){
            RoomType.RoomClass rc = RoomType.RoomClass.getRoomClass(
                    Integer.parseInt(req.getParameter(PARAM_ROOM_CLASS)));
            availableRooms.removeIf(a -> a.getRoomType().getRoomClass() != rc);
            req.getSession().setAttribute(ATTRIBUTE_ROOM_CLASS, String.valueOf(rc.getValue()));
        }

        req.getSession().setAttribute(ATTRIBUTE_ROOMS_LIST, availableRooms);
        return new Response(Response.Direction.Forward, AVAILABLE_ROOMS_JSP);
    }

    public static List<Room> getAvailableRooms(String from, String to) throws JDBCException {
        List<Reservation> reservedRooms;
        List<Request> requestedRooms;
        List<Room> availableRooms;

        availableRooms = RoomService.getAll();

        reservedRooms = ReservationService.getByDate(
                Date.valueOf(from), Date.valueOf(to)
        );
        List<Long> occupiedRoomsId = reservedRooms.stream()
                .map(Reservation::getRoomId).collect(Collectors.toList());
        for (Long i : occupiedRoomsId){
            availableRooms.removeIf(a -> i.equals(a.getId()));
        }

        requestedRooms = RequestService.getByDate(
                Date.valueOf(from), Date.valueOf(to)
        );
        occupiedRoomsId = requestedRooms.stream()
                .filter(a -> (a.getStatus() != Request.Status.ManagerResponse && a.getStatus() != Request.Status.Canceled))
                .map(Request::getRoomId).collect(Collectors.toList());
        for (Long i : occupiedRoomsId){
            availableRooms.removeIf(a -> i.equals(a.getId()));
        }

        return availableRooms;
    }

    @Override
    public String getCommand() {
        return AVAILABLE_ROOMS;
    }
}
