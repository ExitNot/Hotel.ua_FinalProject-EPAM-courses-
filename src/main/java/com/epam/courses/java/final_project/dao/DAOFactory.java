package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.*;

/**
 * Factory of data access objects. Factory method is applied here giving ability to
 * use another database dao implementation in future
 *
 * @author Kostiantyn Kolchenko
 */
public class DAOFactory {

    private final UserDao userDao;
    private final RoomDao roomDao;
    private final ReservationDao reservationDao;
    private final RequestDao requestDao;
    private final RoomTypeDao roomTypeDao;
    private final ImageDao imageDao;
    private static final DAOFactory INSTANCE = new DAOFactory();

    public DAOFactory() {
        userDao = new UserDaoImpl();
        roomDao = new RoomDaoImpl();
        reservationDao = new ReservationDaoImpl();
        requestDao = new RequestDaoImpl();
        roomTypeDao = new RoomTypeDaoImpl();
        imageDao = new ImageDaoImpl();
    }

    public static DAOFactory getInstance() {
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

    public RoomTypeDao getRoomTypeDao() {
        return roomTypeDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }
}
