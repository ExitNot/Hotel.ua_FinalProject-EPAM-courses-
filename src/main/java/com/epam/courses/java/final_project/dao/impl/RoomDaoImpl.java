package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RoomDao;
import com.epam.courses.java.final_project.dao.entity.Room;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.Constant.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class RoomDaoImpl implements RoomDao {

    private final String SQL_ROOM_INSERT = "INSERT INTO rooms (room_number, floor, capacity, beds_types, class) VALUES (?, ?, ?, ?, ?);";
    private final String SQL_ROOM_UPDATE = "UPDATE rooms SET room_number = ?, floor = ?, capacity = ?, beds_types = ?, class = ? WHERE id = ?";
    private final Logger logger = LogManager.getLogger();

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
    public void create(Room obj) throws JDBCException {
        int id = JDBCManager.updateRequest(SQL_ROOM_INSERT, String.valueOf(obj.getRoomNumber()),
                String.valueOf(obj.getCapacity()), obj.getBedType(),
                String.valueOf(obj.getRoomClass().getValue()));;
        obj.setId(id);
    }

    @Override
    public Optional<Room> getById(long id) {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL_SELECT_BY,
                TABLE_ROOM, PARAM_ID, String.valueOf(id)));
    }

    @Override
    public Optional<Room> getByRoomNum(int roomNum) {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL_SELECT_BY,
                TABLE_ROOM, PARAM_ROOM_NUM, String.valueOf(roomNum)));
    }

    @Override
    public List<Room> getAll() {
        return JDBCManager.selectRequest(this, SQL_SELECT_ALL, TABLE_ROOM);
    }

    @Override
    public List<Room> getRoomsByFloor(int floor) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY,
                TABLE_ROOM, PARAM_FLOOR, String.valueOf(floor));
    }

    @Override
    public List<Room> getRoomsByCapacity(int capacity) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY,
                TABLE_ROOM, PARAM_CAPACITY, String.valueOf(capacity));
    }

    @Override
    public List<Room> getRoomsByBedsTypes(String bedsTypes) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY,
                TABLE_ROOM, PARAM_BED_TYPE, bedsTypes);
    }

    @Override
    public List<Room> getRoomsByClass(Room.RoomClass roomClass) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY,
                TABLE_ROOM, PARAM_CLASS, String.valueOf(roomClass.getValue()));
    }

    @Override
    public void update(Room obj) throws JDBCException {
        JDBCManager.updateRequest(SQL_ROOM_UPDATE,
                String.valueOf(obj.getRoomNumber()), String.valueOf(obj.getFloor()), String.valueOf(obj.getCapacity()),
                obj.getBedType(), String.valueOf(obj.getRoomClass().getValue()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(SQL_DELETE, TABLE_ROOM, String.valueOf(id));
    }
}
