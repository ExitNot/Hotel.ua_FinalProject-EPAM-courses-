package com.epam.courses.java.final_project.logic.command.impl;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.logic.command.Command;
import com.epam.courses.java.final_project.logic.command.Response;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.epam.courses.java.final_project.util.constant.CommandConstant.*;

public class CancelReservationCommand implements Command {

    @Override
    public Response execute(HttpServletRequest req, HttpServletResponse resp) throws JDBCException {
        long reservationId = Long.parseLong(req.getParameter(PARAM_RESERVATION_ID));
        DAOFactory.getInstance().getReservationDao().delete(reservationId);

        return new Response(Response.Direction.Redirect, MY_RESERVATIONS_ACT);
    }

    @Override
    public String getCommand() {
        return CANCEL_RESERVATION;
    }
}
