package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.MailManager;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class RequestResponseCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<Request> requestsBundle = (List<Request>) req.getSession().getAttribute("requestBundle");
        List<Long> chosenRooms = new ArrayList<>();

        for (Request r : requestsBundle){
            if (req.getParameter("price" + r.getId()).isEmpty()){
                req.getSession().setAttribute("requestResponseEx", "Price field was empty");
                return new Response(Response.Direction.Forward, "requestResponse.jsp");
            }
            r.setPrice(Double.parseDouble(req.getParameter("price" + r.getId())));
            if (r.getRoomId() == 0){
                if (req.getParameter("chosenRoomId" + r.getId()) == null || req.getParameter("chosenRoomId" + r.getId()).isEmpty()){
                    req.getSession().setAttribute("requestResponseEx", "Room was not chosen");
                    return new Response(Response.Direction.Forward, "requestResponse.jsp");
                }
                chosenRooms.add(Long.parseLong(req.getParameter("chosenRoomId" + r.getId())));
                r.setRoomId(Long.parseLong(req.getParameter("chosenRoomId" + r.getId())));
                r.setRoomNumber(Integer.parseInt(req.getParameter("chosenRoomNumber" + r.getId())));
            }
            r.setManagerAcceptance(Util.getToday());
            r.setStatus(2);
        }

        if (chosenRooms.size() > (new HashSet<Long>(chosenRooms)).size()){
            req.getSession().setAttribute("requestResponseEx", "Same rooms was chosen");
            return new Response(Response.Direction.Forward, "requestResponse.jsp");
        }

        for (Request r : requestsBundle){
            RequestService.update(r);

            for (Request i : RequestService.getAll()){
                if (i.getRoomId() == r.getRoomId() && i.getFrom().before(r.getTo()) &&
                        i.getTo().after(r.getFrom()) && i.getId() != r.getId()){
                    i.setStatus(4);
                    RequestService.update(i);
//                    todo email that room was booked
                }
            }
        }

        Optional<User> oUser = UserService.getById(requestsBundle.get(0).getUserId());
        oUser.ifPresent(user -> MailManager.getInstance().sendEmail(
                user.getEmail(), MailManager.requestResponseMailTemplate(user.getName(), user.getSurname())
        ));
        return new Response(Response.Direction.Redirect, REQUEST_LIST_ACT);
    }

    @Override
    public String getCommand() {
        return REQUEST_RESPONSE;
    }
}
