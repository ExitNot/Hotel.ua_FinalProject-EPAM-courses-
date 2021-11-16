package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RequestDao;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RequestDaoImpl implements RequestDao {
    // todo reformat as others DAO's

    @Override
    public long create(Request obj) throws JDBCException {
        return 0;
    }

    @Override
    public Optional<Request> getById(long id) throws JDBCException {
        return Optional.empty();
    }

    @Override
    public List<Request> getAll() throws JDBCException {
        return null;
    }

    @Override
    public void update(Request obj) throws JDBCException {

    }

    @Override
    public void delete(long id) throws JDBCException {

    }

    @Override
    public Request createEntity(ResultSet rs) throws SQLException {
        return null;
    }

    @Override
    public List<Request> getUserRequests(User user) {
        return null;
    }

    @Override
    public List<Request> getRequestsByStatus(Request.Status status) {
        return null;
    }
}
