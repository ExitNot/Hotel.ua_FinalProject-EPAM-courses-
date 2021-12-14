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
        String lang = req.getSession().getAttribute(ATTRIBUTE_LANG).toString();

        for (Request r : requestsBundle){
            if (req.getParameter("price" + r.getId()).isEmpty()){
                if (lang.equals("ru"))
                    req.getSession().setAttribute(ATTRIBUTE_REQUEST_RESPONSE_EX, "\u0421\u0442\u0440\u043E\u043A\u0430 \u0441\u0442\u043E\u0438\u043C\u043E\u0441\u0442\u044C \u043F\u0443\u0441\u0442\u0430");
                else
                    req.getSession().setAttribute(ATTRIBUTE_REQUEST_RESPONSE_EX, "Price field was empty");
                return new Response(Response.Direction.Forward, REQUEST_RESPONSE_JSP);
            }
            r.setPrice(Double.parseDouble(req.getParameter("price" + r.getId())));
            if (r.getRoomId() == 0){
                if (req.getParameter("chosenRoomId" + r.getId()) == null || req.getParameter("chosenRoomId" + r.getId()).isEmpty()){
                    if (lang.equals("ru"))
                        req.getSession().setAttribute(ATTRIBUTE_REQUEST_RESPONSE_EX, "\u041A\u043E\u043C\u043D\u0430\u0442\u0430 \u043D\u0435 \u0431\u044B\u043B\u0430 \u0432\u044B\u0431\u0440\u0430\u043D\u0430");
                    else
                        req.getSession().setAttribute(ATTRIBUTE_REQUEST_RESPONSE_EX, "Room was not chosen");
                    return new Response(Response.Direction.Forward, REQUEST_RESPONSE_JSP);
                }
                chosenRooms.add(Long.parseLong(req.getParameter("chosenRoomId" + r.getId())));
                r.setRoomId(Long.parseLong(req.getParameter("chosenRoomId" + r.getId())));
                r.setRoomNumber(Integer.parseInt(req.getParameter("chosenRoomNumber" + r.getId())));
            }
            r.setManagerAcceptance(Util.getToday());
            r.setStatus(2);
        }

        if (chosenRooms.size() > (new HashSet<Long>(chosenRooms)).size()){
            if (lang.equals("ru"))
                req.getSession().setAttribute(ATTRIBUTE_REQUEST_RESPONSE_EX, "\u0411\u044B\u043B\u0438 \u0432\u044B\u0431\u0440\u0430\u043D\u044B \u043E\u0434\u0438\u043D\u0430\u043A\u043E\u0432\u044B\u0435 \u043A\u043E\u043C\u043D\u0430\u0442\u044B");
            else
                req.getSession().setAttribute(ATTRIBUTE_REQUEST_RESPONSE_EX, "Same rooms was chosen");
            return new Response(Response.Direction.Forward, REQUEST_RESPONSE_JSP);
        }

        for (Request r : requestsBundle){
            RequestService.update(r);

            for (Request i : RequestService.getAll()){
                if (i.getRoomId() == r.getRoomId() && i.getFrom().before(r.getTo()) &&
                        i.getTo().after(r.getFrom()) && i.getId() != r.getId()){
                    i.setStatus(4);
                    RequestService.update(i);
                }
            }
        }

        Optional<User> oUser = UserService.getById(requestsBundle.get(0).getUserId());
        oUser.ifPresent(user -> MailManager.getInstance().sendEmail(
                user.getEmail(), MailManager.requestResponseMailTemplate(user.getName(), user.getSurname(),
                        req.getSession().getAttribute(ATTRIBUTE_LANG).toString())
        ));
        return new Response(Response.Direction.Redirect, REQUEST_LIST_ACT);
    }

    @Override
    public String getCommand() {
        return REQUEST_RESPONSE;
    }
}
