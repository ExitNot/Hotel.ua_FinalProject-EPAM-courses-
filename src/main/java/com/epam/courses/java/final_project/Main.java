package com.epam.courses.java.final_project;

import com.epam.courses.java.final_project.dao.AbstractDao;
import com.epam.courses.java.final_project.dao.ReservationDao;
import com.epam.courses.java.final_project.dao.UserDao;
import com.epam.courses.java.final_project.dao.entity.Reservation;
import com.epam.courses.java.final_project.dao.entity.User;
import com.epam.courses.java.final_project.dao.impl.ReservationDaoImpl;
import com.epam.courses.java.final_project.dao.impl.UserDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger("trace");

    public static void main(String[] args) {
//        ConnectionPool cp = ConnectionPool.getInstance();
//        System.out.println(logger.getLevel());
//        System.out.println(logger.getName());
        logger.trace("trace");
        logger.info("info");
        logger.error("error");

//        UserDao dao = new UserDaoImpl();
//        ReservationDao reservDao = new ReservationDaoImpl();
//        dao.create(new User(0, "user", "pwd", "+320", "user@gmail.com", User.Role.Customer));
//        List<Reservation> list = reservDao.getOccupiedRoomsReservations(
//                Date.valueOf("1999-08-01"), Date.valueOf("1999-08-05")
//        );
//        System.out.println(list);

    }
}
