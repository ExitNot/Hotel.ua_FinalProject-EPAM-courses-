package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.courses.java.final_project.util.CommandConstant.*;

public class CancelRequestCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        long requestId = Long.parseLong(req.getParameter(PARAM_REQUEST_ID));
        DAOFactory.getInstance().getRequestDao().delete(requestId);

        return new Response(Response.Direction.Redirect, PROFILE_ACT);
    }

    @Override
    public String getCommand() {
        return CANCEL_REQUEST;
    }
}
