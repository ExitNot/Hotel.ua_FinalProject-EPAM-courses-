package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;

import static com.epam.courses.java.final_project.util.CommandConstant.*;
import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class RequestCommand implements Command {

    private final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        if (req.getSession().getAttribute(ATTRIBUTE_ID) == null){
            req.getSession().setAttribute(ATTRIBUTE_LOGIN_ERROR, "You have to login first");
            new Response(Response.Direction.Redirect, INDEX_JSP);
        }
        String from = Util.transformDate(req.getParameter(PARAM_FROM));
        String to = Util.transformDate(req.getParameter(PARAM_TO));

        for (int i = 1; i <= Integer.parseInt(req.getParameter("rooms_amount")); i++ ){
            Request request = new Request(
                Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
                Date.valueOf(from),
                Date.valueOf(to),
                Integer.parseInt(req.getParameter(PARAM_ADULTS_AMOUNT + i) ),
                Integer.parseInt(req.getParameter(PARAM_CHILDREN_AMOUNT + i)),
                Request.Status.ManagerResponse, Util.calcPrice());
            RequestService.create(request);
        }
//        Request request = new Request(
//                Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
//                Date.valueOf(req.getSession().getAttribute(ATTRIBUTE_FROM).toString()),
//                Date.valueOf(req.getSession().getAttribute(ATTRIBUTE_TO).toString()),
//                req.getParameter(), 0, Request.Status.Payment, Util.calcPrice());

//        DAOFactory.getInstance().getRequestDao().create();
        return new Response(Response.Direction.Redirect, PROFILE_ACT);

    }

    @Override
    public String getCommand() {
        return CREATE_REQUEST;
    }
}
