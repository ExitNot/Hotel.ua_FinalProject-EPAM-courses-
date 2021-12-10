package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.User;

import java.util.Optional;

/**
 * Service class that provide access to {@code RoomDao}.
 *
 * @author Kostiantyn Kolchenko
 * @see DAOFactory
 * @see Room
 */
public class UserService {

    public static Optional<User> getById(long id) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().getById(id);
    }
    public static Optional<User> getByEmail(String email) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().getByEmail(email);
    }

    public static long create(User user) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().create(user);
    }

    public static void update(User user) throws JDBCException {
        DAOFactory.getInstance().getUserDao().update(user);
    }

    public static void delete(long id) throws JDBCException {
        DAOFactory.getInstance().getUserDao().delete(id);
    }
}
