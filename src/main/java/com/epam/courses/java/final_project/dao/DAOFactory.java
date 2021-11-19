package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.*;

public class DAOFactory {

    private UserDao userDao;
    private RoomDao roomDao;
    private ReservationDao reservationDao;
    private RequestDao requestDao;
    private RoomTypeDao roomTypeDao;
    private ImageDao imageDao;
    private static final DAOFactory INSTANCE = new DAOFactory();

    public DAOFactory() {
        userDao = new UserDaoImpl();
        roomDao = new RoomDaoImpl();
        reservationDao = new ReservationDaoImpl();
        requestDao = new RequestDaoImpl();
        roomTypeDao = new RoomTypeDaoImpl();
        imageDao = new ImageDaoImpl();
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

    public RoomTypeDao getRoomTypeDao() {
        return roomTypeDao;
    }

    public ImageDao getImageDao() {
        return imageDao;
    }
}
