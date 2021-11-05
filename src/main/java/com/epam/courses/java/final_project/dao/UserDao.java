package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for User.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface UserDao extends AbstractDao<User>{

    Optional<User> getByLogin(String login);

    Optional<User> getByEmail(String email);

    List<User> getUsersByRole(User.Role role);
}
