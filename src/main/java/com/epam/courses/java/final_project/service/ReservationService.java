package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.util.Util;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class ReservationService {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    public static List<Reservation> getByDate(Date from, Date to) throws JDBCException {
        List<Reservation> out = DAOFactory.getInstance().getReservationDao().getByDate(from, to);
        for (Reservation r : out){
            if (!validateWaitingTime(r))
                out.remove(r);
        }
        return out;
    }

    public static List<Reservation> getByUser(Long id) throws JDBCException {
        List<Reservation> out = DAOFactory.getInstance().getReservationDao().getUserReservations(id);
        for (Reservation r : out){
            if (!validateWaitingTime(r))
                out.remove(r);
        }
        return out;
    }

    public static void createByRequest(Request req) throws JDBCException {
        DAOFactory.getInstance().getReservationDao().create(new Reservation(req.getUserId(), req.getRoomId(),
                req.getFrom(), req.getTo(), req.getGuestsAmount()));
    }

    public static void createByRoom(Long userId, Long roomId, Date from, Date to) throws JDBCException {
        DAOFactory.getInstance().getReservationDao().create(new Reservation(userId, roomId, from, to));
    }

    private static boolean validateWaitingTime(Reservation res) throws JDBCException {
        Date today = Util.getToday();

        Calendar c = Calendar.getInstance();
        c.setTime(res.getTo());
        Date deadline = new Date(c.getTimeInMillis());

        if (today.after(deadline)) {
            DAOFactory.getInstance().getReservationDao().delete(res.getId());
            log.info("Request(" + res.getId() + ") was deleted, because was expired");
            return false;
        }
        return true;
    }
}
