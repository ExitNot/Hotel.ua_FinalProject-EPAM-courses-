package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.User;

import java.util.Optional;

public class UserService {

    public static Optional<User> findByEmail(String email) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().getByEmail(email);
    }

    public static long create(User user) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().create(user);
    }

    public static void delete(long id) throws JDBCException {
        DAOFactory.getInstance().getUserDao().delete(id);
    }
}
