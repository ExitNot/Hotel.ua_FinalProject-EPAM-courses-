package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.User;

import java.util.List;

/**
 * DAO interface for Request.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface RequestDao extends AbstractDao<Request> {

    List<Request> getUserRequests(User user);

    List<Request> getRequestsByStatus(Request.Status status);
}
