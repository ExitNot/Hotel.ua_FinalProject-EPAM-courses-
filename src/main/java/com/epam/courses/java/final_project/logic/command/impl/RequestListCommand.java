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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.CommandConstant.ATTRIBUTE_USER_REQUEST_LIST;

public class RequestListCommand implements Command {
    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Request> requests = RequestService.getByUserId((Long) req.getSession().getAttribute(ATTRIBUTE_ID));

        for (Request r : requests){
            Optional<Room> room = RoomService.getById(r.getRoomId());  // todo remove duplicate
            if (room.isPresent()){
                Optional<RoomType> rt = RoomTypeService.getById(room.get().getRoomTypeId());
                r.setRoomNumber(room.get().getRoomNumber());
                rt.ifPresent(r::setRoomType);
            }
        }

        req.getSession().setAttribute(ATTRIBUTE_USER_REQUEST_LIST, requests);
        return new Response(Response.Direction.Redirect, MY_REQUESTS_JSP);
    }

    @Override
    public String getCommand() {
        return REQUEST_LIST;
    }
}
