package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.ReservationDao;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;

import static com.epam.courses.java.final_project.util.constant.Constant.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Reservation DAO implementation for PostgreSQL db.
 *
 * @author Kostiantyn Kolchenko
 */
public class ReservationDaoImpl implements ReservationDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.getInstance().setTableName(SQL.SELECT_BY, TABLE_RESERVATION);
        SELECT_ALL = JDBCManager.getInstance().setTableName(SQL.SELECT_ALL, TABLE_RESERVATION);
        DELETE = JDBCManager.getInstance().setTableName(SQL.DELETE, TABLE_RESERVATION);
    }

    @Override
    public Reservation createEntity(ResultSet rs) throws SQLException {
        return new Reservation(rs.getLong(PARAM_ID),
                rs.getLong(PARAM_USER_ID),
                rs.getLong(PARAM_ROOM_ID),
                rs.getDate(PARAM_DATE_FROM),
                rs.getDate(PARAM_DATE_TO),
                rs.getInt(PARAM_GUESTS_AMOUNT));
    }

    @Override
    public long create(Reservation obj) throws JDBCException {
        long id = JDBCManager.getInstance().updateRequest(SQL.RESERVATION_INSERT,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(), String.valueOf(obj.getGuestsAmount()));
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<Reservation> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.getInstance().selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ID), String.valueOf(id)));
    }

    @Override
    public List<Reservation> getUserReservations(long userId) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SELECT_BY.replace("param", PARAM_USER_ID), String.valueOf(userId));
    }

    @Override
    public List<Reservation> getRoomReservations(long roomId) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SELECT_BY.replace("param", PARAM_ROOM_ID), String.valueOf(roomId));
    }

    @Override
    public List<Reservation> getByDate(Date from, Date to) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SQL.OCCUPIED_ROOMS, to.toString(), from.toString());
    }

    @Override
    public List<Reservation> getAll() throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_ALL);
    }

    @Override
    public long update(Reservation obj) throws JDBCException {
        return JDBCManager.getInstance().updateRequest(SQL.RESERVATION_UPDATE,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(),
                String.valueOf(obj.getGuestsAmount()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.getInstance().updateRequest(DELETE, String.valueOf(id));
    }
}
