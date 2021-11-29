package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.service.RequestService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class CancelRequestCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        RequestService.delete(Long.parseLong(req.getParameter(PARAM_REQUEST_ID)));
        return new Response(Response.Direction.Redirect, MY_REQUESTS_ACT);
    }

    @Override
    public String getCommand() {
        return CANCEL_REQUEST;
    }
}
