package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) {
        return new Response(Response.Direction.Error, HttpServletResponse.SC_NOT_FOUND, req.getServletPath() + " page was not found");
    }

    @Override
    public String getCommand() {
        return NOT_FOUND;
    }
}
