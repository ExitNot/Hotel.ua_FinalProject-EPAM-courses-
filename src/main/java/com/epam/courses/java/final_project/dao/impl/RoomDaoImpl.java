package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RoomDao;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;

import static com.epam.courses.java.final_project.util.Constant.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.setTableName(SQL.SELECT_BY, TABLE_ROOM);
        SELECT_ALL = JDBCManager.setTableName(SQL.SELECT_ALL, TABLE_ROOM);
        DELETE = JDBCManager.setTableName(SQL.DELETE, TABLE_ROOM);
    }

    @Override
    public Room createEntity(ResultSet rs) throws SQLException {
        return new Room(rs.getLong(PARAM_ID),
                rs.getInt(PARAM_ROOM_NUM),
                rs.getInt(PARAM_FLOOR),
                rs.getInt(PARAM_CAPACITY),
                rs.getString(PARAM_BED_TYPE),
                Room.RoomClass.getRoomClass(rs.getInt(PARAM_CLASS)));
    }

    @Override
    public long create(Room obj) throws JDBCException {
        long id = JDBCManager.updateRequest(SQL.ROOM_INSERT, String.valueOf(obj.getRoomNumber()),
                String.valueOf(obj.getCapacity()), obj.getBedType(),
                String.valueOf(obj.getRoomClassValue().getValue()));
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<Room> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ID), String.valueOf(id)));
    }

    @Override
    public Optional<Room> getByRoomNum(int roomNum) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ROOM_NUM), String.valueOf(roomNum)));
    }

    @Override
    public List<Room> getAll() throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_ALL);
    }

    @Override
    public List<Room> getRoomsByFloor(int floor) throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_BY.replace("param", PARAM_FLOOR), String.valueOf(floor));
    }

    @Override
    public List<Room> getRoomsByCapacity(int capacity) throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_BY.replace("param", PARAM_CAPACITY), String.valueOf(capacity));
    }

    @Override
    public List<Room> getRoomsByBedsTypes(String bedsTypes) throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_BY.replace("param", PARAM_BED_TYPE), bedsTypes);
    }

    @Override
    public List<Room> getRoomsByClass(Room.RoomClass roomClass) throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_BY.replace("param", PARAM_CLASS), String.valueOf(roomClass.getValue()));
    }

    @Override
    public void update(Room obj) throws JDBCException {
        JDBCManager.updateRequest(SQL.ROOM_UPDATE,
                String.valueOf(obj.getRoomNumber()), String.valueOf(obj.getFloor()), String.valueOf(obj.getCapacity()),
                obj.getBedType(), String.valueOf(obj.getRoomClassValue().getValue()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(DELETE, String.valueOf(id));
    }
}
