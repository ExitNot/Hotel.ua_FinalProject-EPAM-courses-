package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.RoomTypeService;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class CreateRequestCommand implements Command {

    private final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        if (req.getSession().getAttribute(ATTRIBUTE_ID) == null){
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_EX, "You have to login first");
            return new Response(Response.Direction.Redirect, INDEX_JSP);
        }
        String from = Util.transformDate(req.getParameter(PARAM_FROM));
        String to = Util.transformDate(req.getParameter(PARAM_TO));

        if (from == null){
            Optional<RoomType> rt = RoomTypeService.getById(Long.parseLong(req.getParameter(PARAM_ROOM_TYPE_ID)));
            rt.ifPresent(roomType -> req.getSession().setAttribute(ATTRIBUTE_ROOM_TYPE, roomType));
            return new Response(Response.Direction.Redirect, REQUEST_JSP);
        }

        for (int i = 1; i <= Integer.parseInt(req.getParameter("rooms_amount")); i++ ){  // TODO not always consequent
            String adultsAmount = req.getParameter(PARAM_ADULTS_AMOUNT + i);
            String childrenAmount = req.getParameter(PARAM_CHILDREN_AMOUNT + i);
            if (adultsAmount == null)
                adultsAmount = "0";
            if (childrenAmount == null)
                childrenAmount = "0";
            Request request = new Request(
                Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
                Date.valueOf(from),
                Date.valueOf(to),
                Integer.parseInt(adultsAmount),
                Integer.parseInt(childrenAmount),
                Request.Status.ManagerResponse, Util.calcPrice());
            request.setRc(RoomType.RoomClass.getRoomClass(Integer.parseInt(
                    req.getParameter(PARAM_ROOM_CLASS.replace("room", "room" + i))
            )));
            RequestService.create(request);
        }
        return new Response(Response.Direction.Redirect, MY_REQUESTS_ACT);
    }

    @Override
    public String getCommand() {
        return CREATE_REQUEST;
    }
}
