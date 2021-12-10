package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RequestDao;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.RoomType;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.*;

/**
 * Request DAO implementation for PostgreSQL db.
 *
 * @author Kostiantyn Kolchenko
 */
public class RequestDaoImpl implements RequestDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.getInstance().setTableName(SQL.SELECT_BY, TABLE_REQUEST);
        SELECT_ALL = JDBCManager.getInstance().setTableName(SQL.SELECT_ALL, TABLE_REQUEST);
        DELETE = JDBCManager.getInstance().setTableName(SQL.DELETE, TABLE_REQUEST);
    }

    @Override
    public Request createEntity(ResultSet rs) throws SQLException {
        return new Request(rs.getLong(PARAM_ID),
                rs.getLong(PARAM_USER_ID),
                rs.getLong(PARAM_ROOM_ID),
                rs.getDate(PARAM_DATE_FROM),
                rs.getDate(PARAM_DATE_TO),
                rs.getDate(PARAM_DATE_OF_CUSTOMER_ACCEPTANCE),
                rs.getInt(PARAM_ADULTS_AMOUNT),
                rs.getInt(PARAM_CHILDREN_AMOUNT),
                RoomType.RoomClass.getRoomClass(rs.getInt(PARAM_CLASS)),
                Request.Status.getStatus(rs.getInt(PARAM_STATUS)),
                rs.getDouble(PARAM_PRICE)
        );
    }

    @Override
    public long create(Request obj) throws JDBCException {
        long id;
        if (obj.getRoomId() == 0) {
            id = JDBCManager.getInstance().updateRequest(SQL.REQUEST_INSERT,
                    String.valueOf(obj.getUserId()), null,
                    obj.getFrom().toString(), obj.getTo().toString(),
                    String.valueOf(obj.getAdultsAmount()), String.valueOf(obj.getChildrenAmount()),
                    String.valueOf(obj.getRc().getValue()), String.valueOf(obj.getStatus().getValue()),
                    String.valueOf(obj.getPrice()));
        } else {
            id = JDBCManager.getInstance().updateRequest(SQL.REQUEST_INSERT,
                    String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                    obj.getFrom().toString(), obj.getTo().toString(),
                    String.valueOf(obj.getAdultsAmount()), String.valueOf(obj.getChildrenAmount()),
                    String.valueOf(obj.getRc().getValue()), String.valueOf(obj.getStatus().getValue()),
                    String.valueOf(obj.getPrice()));
        }
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<Request> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.getInstance().selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ID), String.valueOf(id)));
    }

    @Override
    public List<Request> getUserRequests(Long user_id) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SELECT_BY.replace("param", PARAM_USER_ID), String.valueOf(user_id));
    }

    @Override
    public List<Request> getRequestsByStatus(Request.Status status) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SELECT_BY.replace("param", PARAM_STATUS), String.valueOf(status.getValue()));
    }

    @Override
    public List<Request> getByDate(Date from, Date to) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SQL.REQUESTED_ROOMS, to.toString(), from.toString());
    }

    @Override
    public List<Request> getAll() throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_ALL);
    }

    @Override
    public long update(Request obj) throws JDBCException {
        return JDBCManager.getInstance().updateRequest(SQL.REQUEST_UPDATE,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(),
                String.valueOf(obj.getAdultsAmount()), String.valueOf(obj.getChildrenAmount()),
                String.valueOf(obj.getRc().getValue()), String.valueOf(obj.getStatus().getValue()),
                String.valueOf(obj.getPrice()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.getInstance().updateRequest(DELETE, String.valueOf(id));
    }
}
