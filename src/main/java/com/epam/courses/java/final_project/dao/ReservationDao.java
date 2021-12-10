package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for {@code Reservation}.
 *
 * @author Kostiantyn Kolchenko
 * @see Reservation
 */
public interface ReservationDao extends AbstractDao<Reservation> {

    List<Reservation> getUserReservations(long userId) throws JDBCException;

    List<Reservation> getRoomReservations(long roomId) throws JDBCException;

    List<Reservation> getByDate(Date from, Date to) throws JDBCException;
}
