package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RoomTypeService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class IndexCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        List<RoomType> roomTypes = RoomTypeService.getAll();
        String lang = null;
        if (req.getParameter(ATTRIBUTE_LANG) != null)
            lang = req.getParameter(ATTRIBUTE_LANG);
        else if (req.getSession().getAttribute(ATTRIBUTE_LANG) != null)
            lang = req.getSession().getAttribute(ATTRIBUTE_LANG).toString();

        for (RoomType i : roomTypes){
            i.setImgPaths(RoomTypeService.getImg(i.getId()));
            i.setLang(lang);
        }

        req.getSession().setAttribute(ATTRIBUTE_ROOM_TYPES_LIST, roomTypes);
        return new Response(Response.Direction.Forward, INDEX_JSP);
    }

    @Override
    public String getCommand() {
        return INDEX;
    }
}
