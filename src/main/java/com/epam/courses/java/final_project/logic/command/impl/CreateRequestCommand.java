package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.RequestService;
import com.epam.courses.java.final_project.service.RoomService;
import com.epam.courses.java.final_project.service.RoomTypeService;
import com.epam.courses.java.final_project.service.UserService;
import com.epam.courses.java.final_project.util.MailManager;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Date;
import java.util.Calendar;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class CreateRequestCommand implements Command {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        String from = req.getParameter(PARAM_FROM);
        String to = req.getParameter(PARAM_TO);

        if (from == null){
            Optional<RoomType> rt = RoomTypeService.getById(Long.parseLong(req.getParameter(PARAM_ROOM_TYPE_ID)));
            rt.ifPresent(roomType -> req.getSession().setAttribute(ATTRIBUTE_ROOM_TYPE, roomType));
            return new Response(Response.Direction.Redirect, REQUEST_JSP);
        }
        if (from.isEmpty()){
            Calendar c = Calendar.getInstance();
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.MINUTE, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MILLISECOND, 0);
            from = new Date(c.getTimeInMillis()).toString();
            c.add(Calendar.DAY_OF_YEAR, 1);
            to = new Date(c.getTimeInMillis()).toString();
        } else {
            from = Util.transformDate(from);
            to = Util.transformDate(to);
        }

        if (req.getParameter(PARAM_ROOM_ID) != null){
            String adultsAmount = req.getParameter(PARAM_ADULTS_AMOUNT);
            String childrenAmount = req.getParameter(PARAM_CHILDREN_AMOUNT);
            Optional<Room> room = RoomService.getById(Long.valueOf(req.getParameter(PARAM_ROOM_ID)));
            if (room.isPresent()){
                Optional<RoomType> rt = RoomTypeService.getById(room.get().getRoomTypeId());
                if (rt.isPresent()){
                    Request request = new Request(
                            Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
                            Long.parseLong(req.getParameter(PARAM_ROOM_ID)),
                            Date.valueOf(from),
                            Date.valueOf(to),
                            Integer.parseInt(adultsAmount),
                            Integer.parseInt(childrenAmount),
                            Request.Status.ManagerResponse, 0
                    );
                    request.setPrice(Util.calcPrice(rt.get().getCapacity(),
                            Integer.parseInt(childrenAmount), rt.get().getRoomClass()));
                    request.setRc(rt.get().getRoomClass());
                    RequestService.create(request);
                } else
                    log.warn("Room type was not found");
            } else
                log.warn("Room was not found");
        } else {
            for (int i = 1; i <= 5; i++ ){
                String adultsAmount = req.getParameter(PARAM_ADULTS_AMOUNT + i);
                String childrenAmount = req.getParameter(PARAM_CHILDREN_AMOUNT + i);
                if (adultsAmount == null)
                    continue;
                Request request = new Request(
                        Long.parseLong(req.getSession().getAttribute(ATTRIBUTE_ID).toString()),
                        Date.valueOf(from),
                        Date.valueOf(to),
                        Integer.parseInt(adultsAmount),
                        Integer.parseInt(childrenAmount),
                        Request.Status.ManagerResponse, 0);
                request.setRc(RoomType.RoomClass.getRoomClass(Integer.parseInt(
                        req.getParameter(PARAM_ROOM_CLASS.replace("room", "room" + i)))));
                request.setPrice(Util.calcPrice(Integer.parseInt(adultsAmount) + Integer.parseInt(childrenAmount),
                        Integer.parseInt(childrenAmount), request.getRc()));
                RequestService.create(request);
            }
        }

        Optional<User> oUser = UserService.getById((Long) req.getSession().getAttribute(ATTRIBUTE_ID));
        oUser.ifPresent(user -> MailManager.getInstance().sendEmail(user.getEmail(),
                MailManager.creatingRequestMailTemplate(user.getName(), user.getSurname())));
        return new Response(Response.Direction.Redirect, MY_REQUESTS_ACT);
    }

    @Override
    public String getCommand() {
        return CREATE_REQUEST;
    }
}
