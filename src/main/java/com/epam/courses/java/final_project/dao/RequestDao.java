package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Request;

import java.sql.Date;
import java.util.List;

/**
 * DAO interface for Request.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface RequestDao extends AbstractDao<Request> {

    List<Request> getUserRequests(Long user_id) throws JDBCException;

    List<Request> getRequestsByStatus(Request.Status status) throws JDBCException;

    List<Request> getRequestsByDate(Date from, Date to) throws JDBCException;
}
