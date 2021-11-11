package com.epam.courses.java.final_project.logic.controller;

import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.CommandContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    private final Logger LOGGER = LogManager.getLogger(LOG_TRACE);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqName = req.getParameter("command");
        Command command = CommandContainer.getInstance().getCommandGet(reqName);
        LOGGER.trace("command = " + reqName);

//        req.getRequestDispatcher(command.execute(req, resp)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String reqName = req.getParameter("command");
        Command command = CommandContainer.getInstance().getCommandPost(reqName);
        LOGGER.trace("command = " + reqName);
        req.setAttribute("command", reqName);

//        req.getRequestDispatcher(command.execute(req, resp)).forward(req, resp);
        req.getRequestDispatcher("mocker.jsp").forward(req, resp);
    }
}
