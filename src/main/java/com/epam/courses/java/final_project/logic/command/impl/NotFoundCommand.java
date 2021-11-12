package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;

import static com.epam.courses.java.final_project.util.CommandConstant.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NotFoundCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) {
        return null;
    }

    @Override
    public String getCommand() {
        return NOT_FOUND;
    }
}
