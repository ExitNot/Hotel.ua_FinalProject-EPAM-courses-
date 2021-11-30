package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.RoomTypeService;
import com.epam.courses.java.final_project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.logic.command.impl.AvailableRoomsCommand.getAvailableRooms;
import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class RequestCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        long id = Long.parseLong(req.getParameter(PARAM_REQUEST_ID));
        long assignedRoomId = 0;
        Optional<Request> oRequest = RequestService.getById(id);
        List<Room> availableRooms = null;

        if (oRequest.isPresent()){
            Request r = oRequest.get();
            availableRooms = getAvailableRooms(r.getFrom().toString(), r.getTo().toString());

            if (req.getParameter(PARAM_ASSIGNED_ROOM_ID) != null){
                assignedRoomId = Long.parseLong(req.getParameter(PARAM_ASSIGNED_ROOM_ID));
                r.setRoomId(assignedRoomId);
                RequestService.update(r);
            }

            if (r.getRoomId() != 0)
                RoomService.getById(r.getRoomId()).ifPresent(room -> r.setRoomNumber(room.getRoomNumber()));
            UserService.getById(r.getUserId()).ifPresent(user -> r.setUserEmail(user.getEmail()));

            //        Insert RoomType
            for (Room room : availableRooms){
                RoomTypeService.getById(room.getRoomTypeId()).ifPresent(room::setRoomType);
            }

            availableRooms.removeIf(a -> a.getRoomType().getCapacity() < r.getGuestsAmount());

            req.getSession().setAttribute(ATTRIBUTE_REQUEST, r);
            req.getSession().setAttribute(ATTRIBUTE_ROOMS_LIST, availableRooms);
        }

        return new Response(Response.Direction.Redirect, REQUEST_RESPONSE_JSP);
    }

    @Override
    public String getCommand() {
        return REQUEST;
    }
}
