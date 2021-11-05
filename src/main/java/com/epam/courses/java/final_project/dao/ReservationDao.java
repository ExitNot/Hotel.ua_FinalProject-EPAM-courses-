package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.entity.Reservation;
import com.epam.courses.java.final_project.dao.entity.Room;
import com.epam.courses.java.final_project.dao.entity.User;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for Reservation.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface ReservationDao extends AbstractDao<Reservation>{

    List<Reservation> getUserReservations(User user);

    List<Reservation> getRoomReservations(Room room);

    List<Reservation> getOccupiedRoomsReservations(Date from, Date to);
}
