package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RoomTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class RoomTypeCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        long typeId = Long.parseLong(req.getParameter(PARAM_ROOM_TYPE_ID));

        Optional<RoomType> rt = RoomTypeService.getById(typeId);

        if (rt.isPresent())
            rt.get().setImgPaths(RoomTypeService.getImg(rt.get().getId()));

        rt.ifPresent(roomType -> req.getSession().setAttribute(ATTRIBUTE_ROOM_TYPE, roomType));
        return new Response(Response.Direction.Redirect, ROOM_TYPE_JSP);
    }

    @Override
    public String getCommand() {
        return ROOM_TYPE;
    }
}
