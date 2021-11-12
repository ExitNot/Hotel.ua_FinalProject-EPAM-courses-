package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.RequestDaoImpl;
import com.epam.courses.java.final_project.dao.impl.ReservationDaoImpl;
import com.epam.courses.java.final_project.dao.impl.RoomDaoImpl;
import com.epam.courses.java.final_project.dao.impl.UserDaoImpl;

public class DAOFactory {

    private UserDao userDao;
    private RoomDao roomDao;
    private ReservationDao reservationDao;
    private RequestDao requestDao;
    private static final DAOFactory INSTANCE = new DAOFactory();

    public DAOFactory() {
        userDao = new UserDaoImpl();
        roomDao = new RoomDaoImpl();
        reservationDao = new ReservationDaoImpl();
        requestDao = new RequestDaoImpl();
    }

    public static DAOFactory getInstance(){
        return INSTANCE;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public RoomDao getRoomDao() {
        return roomDao;
    }

    public ReservationDao getReservationDao() {
        return reservationDao;
    }

    public RequestDao getRequestDao() {
        return requestDao;
    }
}
