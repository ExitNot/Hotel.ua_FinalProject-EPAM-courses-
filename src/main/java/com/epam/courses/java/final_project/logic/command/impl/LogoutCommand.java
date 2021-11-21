package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.courses.java.final_project.util.CommandConstant.*;

public class LogoutCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        req.getSession().removeAttribute(ATTRIBUTE_ID);
        req.getSession().removeAttribute(ATTRIBUTE_ROLE);
        return new Response(Response.Direction.Redirect, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return LOGOUT;
    }
}
