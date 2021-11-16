package com.epam.courses.java.final_project.logic.controller;

import com.epam.courses.java.final_project.Main;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.CommandContainer;
import com.epam.courses.java.final_project.logic.command.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Objects;

import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class Controller extends HttpServlet {

    private final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqName = req.getParameter("command");
        Command command;
        command = CommandContainer.getInstance().getCommandGet(
                Objects.requireNonNullElseGet(
                        reqName, () -> req.getServletPath().substring(1).replace(".act", "")
                )
        );
        log.trace("get command: " + command.getCommand());

        // todo track errors here
        try {
            responseHandler(command.execute(req, resp), req, resp);
        } catch (JDBCException e) {
            log.error(e);
            req.getSession().setAttribute("exception", e);
            resp.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqName = req.getParameter("command");
        String path = req.getServletPath();
        Command command;

        if (path.matches("^*\\?*")){
            req.getSession().setAttribute("linkVar", path.substring(path.indexOf("?")));
            path = path.substring(0, path.indexOf("?"));
        }

        String finalPath = path;
        command = CommandContainer.getInstance().getCommandPost(
                Objects.requireNonNullElseGet(
                        reqName, () -> finalPath.substring(1).replace(".act", "")
                )
        );
        log.trace("post command: " + command.getCommand());

        // todo track errors here
        try {
            responseHandler(command.execute(req, resp), req, resp);
        } catch (JDBCException e) {
            log.error(e.getMessage());
            req.getSession().setAttribute("exception", e.getMessage());
            resp.sendRedirect("error.jsp");
        }
    }

    private void responseHandler(Response response, HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        log.trace(response.toString());
        if (response.getDirection().name().equals("Forward")){
            req.getRequestDispatcher(response.getResponse()).forward(req, resp);
        } else {
            resp.sendRedirect(response.getResponse());
        }
    }
}
