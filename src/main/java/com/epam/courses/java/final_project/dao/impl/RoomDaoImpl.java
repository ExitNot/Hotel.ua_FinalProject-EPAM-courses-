package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RoomDao;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;

import static com.epam.courses.java.final_project.util.constant.Constant.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Room DAO implementation for PostgreSQL db.
 *
 * @author Kostiantyn Kolchenko
 */
public class RoomDaoImpl implements RoomDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.getInstance().setTableName(SQL.SELECT_BY, TABLE_ROOM);
        SELECT_ALL = JDBCManager.getInstance().setTableName(SQL.SELECT_ALL, TABLE_ROOM);
        DELETE = JDBCManager.getInstance().setTableName(SQL.DELETE, TABLE_ROOM);
    }

    @Override
    public Room createEntity(ResultSet rs) throws SQLException {
        return new Room(rs.getLong(PARAM_ID),
                rs.getInt(PARAM_ROOM_NUM),
                rs.getInt(PARAM_FLOOR),
                rs.getLong(PARAM_ROOM_TYPE_ID));
    }

    @Override
    public long create(Room obj) throws JDBCException {
        long id = JDBCManager.getInstance().updateRequest(SQL.ROOM_INSERT,
                String.valueOf(obj.getRoomNumber()),
                String.valueOf(obj.getRoomTypeId()));
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<Room> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.getInstance().selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ID), String.valueOf(id)));
    }

    @Override
    public Optional<Room> getByRoomNum(int roomNum) throws JDBCException {
        return Optional.ofNullable(JDBCManager.getInstance().selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ROOM_NUM), String.valueOf(roomNum)));
    }

    @Override
    public List<Room> getAll() throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_ALL);
    }

    @Override
    public List<Room> getRoomsByFloor(int floor) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_BY.replace("param", PARAM_FLOOR), String.valueOf(floor));
    }

    @Override
    public long update(Room obj) throws JDBCException {
        return JDBCManager.getInstance().updateRequest(SQL.ROOM_UPDATE,
                String.valueOf(obj.getRoomNumber()),
                String.valueOf(obj.getFloor()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.getInstance().updateRequest(DELETE, String.valueOf(id));
    }
}
