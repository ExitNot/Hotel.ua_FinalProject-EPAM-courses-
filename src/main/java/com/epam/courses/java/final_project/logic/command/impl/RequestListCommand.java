package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class RequestListCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Request> requests;
        Cookie waitingForResponseCB = new Cookie("waitingForResponseCB", "true");

        if (req.getParameter(PARAM_SEARCH_BTN) != null){
            if (req.getParameter(PARAM_WAITING_FOR_RESPONSE) != null){
                resp.addCookie(waitingForResponseCB);
                requests = RequestService.getByStatus(Request.Status.ManagerResponse);
            } else {
                waitingForResponseCB.setValue(null);
                resp.addCookie(waitingForResponseCB);
                requests = RequestService.getAll();
            }
        } else {
            resp.addCookie(waitingForResponseCB);
            requests = RequestService.getByStatus(Request.Status.ManagerResponse);
        }

        for (Request r : requests){
            if (r.getRoomId() != 0){
                RoomService.getById(r.getRoomId()).ifPresent(room -> r.setRoomNumber(room.getRoomNumber()));
                UserService.getById(r.getUserId()).ifPresent(user -> r.setUserEmail(user.getEmail()));
            }
        }

        req.getSession().setAttribute(ATTRIBUTE_REQUEST_LIST, requests);
        return new Response(Response.Direction.Redirect, REQUEST_LIST_JSP);
    }

    @Override
    public String getCommand() {
        return REQUEST_LIST;
    }
}
