package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.model.User;

import java.sql.Date;
import java.util.List;

import static com.epam.courses.java.final_project.util.Constant.SQL;

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
