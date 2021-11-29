package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.util.Util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class RequestResponseCommand implements Command {
    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {

        Optional<Request> request = RequestService.getById(Long.parseLong(req.getParameter("requestId")));
        if (request.isPresent()){
            Request r = request.get();
            if (req.getParameter("price").isEmpty()){
                req.getSession().setAttribute("requestResponseEx", "Price field was empty");
                return new Response(Response.Direction.Forward, "requestResponse.jsp");
            }
            r.setPrice(Double.parseDouble(req.getParameter("price")));
            if (r.getRoomId() == 0){
                if (req.getParameter("chosenRoomId") == null){
                    req.getSession().setAttribute("requestResponseEx", "Room was not chosen");
                    return new Response(Response.Direction.Forward, "requestResponse.jsp");
                }
                r.setRoomId(Long.parseLong(req.getParameter("chosenRoomId")));
                r.setRoomNumber(Integer.parseInt(req.getParameter("chosenRoomNumber")));
            }
            r.setManagerAcceptance(Util.getToday());
            r.setStatus(2);
            RequestService.update(r);
        }

        return new Response(Response.Direction.Redirect, REQUEST_LIST_ACT);
    }

    @Override
    public String getCommand() {
        return REQUEST_RESPONSE;
    }
}
