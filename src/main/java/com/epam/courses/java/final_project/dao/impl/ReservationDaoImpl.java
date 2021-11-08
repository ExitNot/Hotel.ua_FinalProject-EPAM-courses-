package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.ReservationDao;
import com.epam.courses.java.final_project.dao.entity.Reservation;
import com.epam.courses.java.final_project.dao.entity.Room;
import com.epam.courses.java.final_project.dao.entity.User;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;

import static com.epam.courses.java.final_project.util.Constant.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ReservationDaoImpl implements ReservationDao {

    private final String SQL_RESERVATION_INSERT = "INSERT INTO reservations (user_id, room_id, date_from, date_to, guests_amount) VALUES (?, ?, ?, ?, ?);";
    private final String SQL_OCCUPIED_ROOMS = "SELECT * FROM reservations WHERE (date_from >= ? AND date_from <= ?) OR (date_to <= ? AND date_to >= ?) OR (date_from < ? AND date_to > ?);";
    private final String SQL_RESERVATION_UPDATE = "UPDATE reservations SET user_id = ?, room_id = ?, date_from = ?, date_to = ?, guests_amount = ? WHERE id = ?";

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
        int id = JDBCManager.updateRequest(SQL_RESERVATION_INSERT,
                String.valueOf(obj.getId()), String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()),
                obj.getFrom().toString(), obj.getTo().toString(), String.valueOf(obj.getGuests_amount()));
        obj.setId(id);
    }

    @Override
    public Optional<Reservation> getById(long id) {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL_SELECT_BY,
                TABLE_RESERVATION, PARAM_ID, String.valueOf(id)));
    }

    @Override
    public List<Reservation> getAll() {
        return JDBCManager.selectRequest(this, SQL_SELECT_ALL, TABLE_RESERVATION);
    }

    @Override
    public List<Reservation> getUserReservations(long userId) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY,
                TABLE_RESERVATION, PARAM_USER_ID, String.valueOf(userId));
    }

    @Override
    public List<Reservation> getRoomReservations(long roomId) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY,
                TABLE_RESERVATION, PARAM_ROOM_ID, String.valueOf(roomId));
    }

    @Override
    public List<Reservation> getOccupiedRoomsReservations(Date from, Date to) {
        return JDBCManager.selectRequest(this, SQL_OCCUPIED_ROOMS,
                from.toString(), to.toString(), to.toString(), from.toString(), from.toString(), to.toString());
    }

    @Override
    public void update(Reservation obj) throws JDBCException {
        JDBCManager.updateRequest(SQL_RESERVATION_UPDATE,
                String.valueOf(obj.getUserId()), String.valueOf(obj.getRoomId()), obj.getFrom().toString(),
                obj.getTo().toString(), String.valueOf(obj.getGuests_amount()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(SQL_DELETE, TABLE_RESERVATION, String.valueOf(id));
    }
}
