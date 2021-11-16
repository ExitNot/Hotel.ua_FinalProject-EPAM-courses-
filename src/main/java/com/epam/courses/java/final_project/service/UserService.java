package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.User;

import java.util.Optional;

public class UserService {

    public static Optional<User> findByLogin(String login) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().getByLogin(login);
    }

    public static long create(User user) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().create(user);
    }

    public static void delete(long id) throws JDBCException {
        DAOFactory.getInstance().getUserDao().delete(id);
    }
}
