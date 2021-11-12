package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.User;

import java.util.Optional;

public class UserService {

    public static Optional<User> findByLogin(String login) throws JDBCException {
        return DAOFactory.getInstance().getUserDao().getByLogin(login);
    }
}
