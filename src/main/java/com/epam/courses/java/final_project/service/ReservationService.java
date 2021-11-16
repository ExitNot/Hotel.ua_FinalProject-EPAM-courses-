package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.User;

import java.sql.Date;
import java.util.List;

public class ReservationService {

    public static List<Reservation> getByDate(Date from, Date to) throws JDBCException {
        return DAOFactory.getInstance().getReservationDao().getOccupiedRoomsReservations(from, to);
    }

    public static List<Reservation> getByUser(Long id) throws JDBCException {
        return DAOFactory.getInstance().getReservationDao().getUserReservations(id);
    }

    public static void createByRoom(Long userId, Long roomId, Date from, Date to) throws JDBCException {
        DAOFactory.getInstance().getReservationDao().create(new Reservation(userId, roomId, from, to));
    }
}
