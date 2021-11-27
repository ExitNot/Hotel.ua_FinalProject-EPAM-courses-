package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.REQUEST_RESPONSE;

public class RequestResponseCommand implements Command {
    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {


        return null;
    }

    @Override
    public String getCommand() {
        return REQUEST_RESPONSE;
    }
}
