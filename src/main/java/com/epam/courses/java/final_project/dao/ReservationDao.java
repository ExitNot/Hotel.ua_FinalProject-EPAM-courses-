package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for Reservation.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface ReservationDao extends AbstractDao<Reservation>{

    List<Reservation> getUserReservations(long userId) throws JDBCException;

    List<Reservation> getRoomReservations(long roomId) throws JDBCException;

    List<Reservation> getOccupiedRoomsReservations(Date from, Date to) throws JDBCException;
}
