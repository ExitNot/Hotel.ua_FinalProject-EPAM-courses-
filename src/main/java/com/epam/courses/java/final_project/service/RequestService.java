package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Request;

import java.sql.Date;
import java.util.List;

public class RequestService {

    public static List<Request> getByDate(Date from, Date to) throws JDBCException {
        return DAOFactory.getInstance().getRequestDao().getRequestsByDate(from, to);
    }

    public static List<Request> getByUser(Long id) throws JDBCException {
        return DAOFactory.getInstance().getRequestDao().getUserRequests(id);
    }
}
