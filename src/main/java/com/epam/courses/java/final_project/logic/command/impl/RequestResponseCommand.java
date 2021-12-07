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

import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class RequestResponseCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {

        List<Request> requestsBundle = (List<Request>) req.getSession().getAttribute("requestBundle");  // RequestService.getBundleRequest(request.get().getId(), request.get().getFrom(), request.get().getTo())
//        log.trace("requestsBundle =========================");
//        log.trace(requestsBundle);
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
                r.setRoomId(Long.parseLong(req.getParameter("chosenRoomId" + r.getId())));
                r.setRoomNumber(Integer.parseInt(req.getParameter("chosenRoomNumber" + r.getId())));
            }
            r.setManagerAcceptance(Util.getToday());
            r.setStatus(2);
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
//        req.getSession().setAttribute("requestBundle", requestsBundle);
//            Optional<User> user = UserService.getById(r.getUserId());
//            MailManager.getInstance().sendEmail(r.getUserEmail(),
//                    MailManager.requestResponseMailTemplate(user.get().getName(), user.get().getSurname()));
//        Optional<Request> request = RequestService.getById(Long.parseLong(req.getParameter(PARAM_REQUEST_ID)));
//        if (request.isPresent()){
//
//        } else {
//            log.error("Request was not found");
//        }

        return new Response(Response.Direction.Redirect, REQUEST_LIST_ACT);
    }

    @Override
    public String getCommand() {
        return REQUEST_RESPONSE;
    }
}
