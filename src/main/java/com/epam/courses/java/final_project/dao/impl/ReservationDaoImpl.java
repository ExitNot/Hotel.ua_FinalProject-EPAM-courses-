package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.ReservationDao;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;

import static com.epam.courses.java.final_project.util.Constant.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl implements ReservationDao {

    @Override
    public Reservation createEntity(ResultSet rs) throws SQLException {
        return new Reservation(rs.getLong(PARAM_ID),
                rs.getInt(PARAM_USER_ID),
                rs.getInt(PARAM_ROOM_ID),
                rs.getDate(PARAM_DATE_FROM),
                rs.getDate(PARAM_DATE_TO),
                rs.getInt(PARAM_GUESTS_AMOUNT));
    }

    @Override
    public void create(Reservation obj) throws JDBCException {
        int id = JDBCManager.updateRequest(SQL.RESERVATION_INSERT,
                String.valueOf(obj.getId()), String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(), String.valueOf(obj.getGuests_amount()));
        obj.setId(id);
    }

    @Override
    public Optional<Reservation> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL.SELECT_BY,
                TABLE_RESERVATION, PARAM_ID, String.valueOf(id)));
    }

    @Override
    public List<Reservation> getAll() throws JDBCException {
        return JDBCManager.selectRequest(this, SQL.SELECT_ALL, TABLE_RESERVATION);
    }

    @Override
    public List<Reservation> getUserReservations(long userId) throws JDBCException {
        return JDBCManager.selectRequest(this, SQL.SELECT_BY,
                TABLE_RESERVATION, PARAM_USER_ID, String.valueOf(userId));
    }

    @Override
    public List<Reservation> getRoomReservations(long roomId) throws JDBCException {
        return JDBCManager.selectRequest(this, SQL.SELECT_BY,
                TABLE_RESERVATION, PARAM_ROOM_ID, String.valueOf(roomId));
    }

    @Override
    public List<Reservation> getOccupiedRoomsReservations(Date from, Date to) throws JDBCException {
        return JDBCManager.selectRequest(this, SQL.OCCUPIED_ROOMS,
                from.toString(), to.toString(), to.toString(), from.toString(), from.toString(), to.toString());
    }

    @Override
    public void update(Reservation obj) throws JDBCException {
        JDBCManager.updateRequest(SQL.RESERVATION_UPDATE,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()), obj.getFrom().toString(),
                obj.getTo().toString(), String.valueOf(obj.getGuests_amount()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(SQL.DELETE, TABLE_RESERVATION, String.valueOf(id));
    }
}
