package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.courses.java.final_project.util.CommandConstant.INDEX;
import static com.epam.courses.java.final_project.util.CommandConstant.INDEX_JSP;

public class IndexCommand implements Command {
    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) {
        return new Response(Response.Direction.Forward, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return INDEX;
    }
}
