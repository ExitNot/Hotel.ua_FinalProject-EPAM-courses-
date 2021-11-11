package com.epam.courses.java.final_project.logic.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {

    String execute(HttpServletRequest req,
                   HttpServletResponse resp);

}
