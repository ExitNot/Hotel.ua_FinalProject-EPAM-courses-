package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RequestDao;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.Constant.*;

public class RequestDaoImpl implements RequestDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.setTableName(SQL.SELECT_BY, TABLE_REQUEST);
        SELECT_ALL = JDBCManager.setTableName(SQL.SELECT_ALL, TABLE_REQUEST);
        DELETE = JDBCManager.setTableName(SQL.DELETE, TABLE_REQUEST);
    }

    @Override
    public Request createEntity(ResultSet rs) throws SQLException {
        return new Request(rs.getLong(PARAM_ID),
                rs.getLong(PARAM_USER_ID),
                rs.getLong(PARAM_ROOM_ID),
                rs.getDate(PARAM_DATE_FROM),
                rs.getDate(PARAM_DATE_TO),
                rs.getInt(PARAM_ADULTS_AMOUNT),
                rs.getInt(PARAM_CHILDREN_AMOUNT),
                Request.Status.getStatus(rs.getInt(PARAM_STATUS)),
                rs.getInt(PARAM_PRICE)
        );
    }

    @Override
    public long create(Request obj) throws JDBCException {
        long id = JDBCManager.updateRequest(SQL.REQUEST_INSERT,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(),
                String.valueOf(obj.getAdultsAmount()), String.valueOf(obj.getChildrenAmount()),
                String.valueOf(obj.getStatus().getValue()), String.valueOf(obj.getPrice()));
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<Request> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ID), String.valueOf(id)));
    }

    @Override
    public List<Request> getUserRequests(Long user_id) throws JDBCException {
        return JDBCManager.selectRequest(this,
                SELECT_BY.replace("param", PARAM_USER_ID), String.valueOf(user_id));
    }

    @Override
    public List<Request> getRequestsByStatus(Request.Status status) throws JDBCException {
        return JDBCManager.selectRequest(this,
                SELECT_BY.replace("param", PARAM_STATUS), String.valueOf(status.getValue()));
    }

    @Override
    public List<Request> getRequestsByDate(Date from, Date to) throws JDBCException {
        return JDBCManager.selectRequest(this,
                SQL.REQUESTED_ROOMS, to.toString(), from.toString());
    }

    @Override
    public List<Request> getAll() throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_ALL);
    }

    @Override
    public void update(Request obj) throws JDBCException {
        JDBCManager.updateRequest(SQL.REQUEST_UPDATE,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(),
                String.valueOf(obj.getAdultsAmount()), String.valueOf(obj.getChildrenAmount()),
                String.valueOf(obj.getStatus().getValue()), String.valueOf(obj.getPrice()),
                String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(DELETE, String.valueOf(id));
    }
}
