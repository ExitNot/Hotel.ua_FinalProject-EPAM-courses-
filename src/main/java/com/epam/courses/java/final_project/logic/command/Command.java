package com.epam.courses.java.final_project.logic.command;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface Command {
//    todo resp remove
    Response execute(HttpServletRequest req,
                   HttpServletResponse resp) throws JDBCException;

    /**
     * Function to identify command, and make logging easier
     *
     * @return {@code String} name of the command
     * */
    String getCommand();
}
