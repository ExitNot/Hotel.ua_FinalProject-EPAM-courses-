package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.RoomTypeDao;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.RoomType;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.*;

public class RoomTypeDaoImpl implements RoomTypeDao {

    private static final String SELECT_BY;
    private static final String SELECT_IMG;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.setTableName(SQL.SELECT_BY, TABLE_ROOM_TYPE);
        SELECT_ALL = JDBCManager.setTableName(SQL.SELECT_ALL, TABLE_ROOM_TYPE);
        DELETE = JDBCManager.setTableName(SQL.DELETE, TABLE_ROOM_TYPE);
        SELECT_IMG = JDBCManager.setTableName(SQL.SELECT_BY, TABLE_IMG);
    }

    @Override
    public RoomType createEntity(ResultSet rs) throws SQLException {
        return new RoomType(rs.getLong(PARAM_ID),
                rs.getInt(PARAM_CAPACITY),
                rs.getString(PARAM_BED_TYPE),
                RoomType.RoomClass.getRoomClass(rs.getInt(PARAM_CLASS)),
                rs.getString(PARAM_DESCRIPTION));
    }

    @Override
    public long create(RoomType obj) throws JDBCException {
        return JDBCManager.updateRequest(SQL.ROOM_TYPE_INSERT,
                String.valueOf(obj.getCapacity()),
                obj.getBedsType(),
                String.valueOf(obj.getRoomClass().getValue()),
                obj.getDescription());
    }

    @Override
    public Optional<RoomType> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this,
                SELECT_BY.replace("param", PARAM_ID), String.valueOf(id)));
    }

    @Override
    public List<RoomType> getRoomTypesByCapacity(int capacity) throws JDBCException {
        return JDBCManager.selectRequest(this,
                SELECT_BY.replace("param", PARAM_CAPACITY), String.valueOf(capacity));
    }

    @Override
    public List<RoomType> getRoomTypesByBedsTypes(String bedsTypes) throws JDBCException {
        return JDBCManager.selectRequest(this,
                SELECT_BY.replace("param", PARAM_BED_TYPE), bedsTypes);
    }

    @Override
    public List<RoomType> getRoomTypesByClass(RoomType.RoomClass roomClass) throws JDBCException {
        return JDBCManager.selectRequest(this,
                SELECT_BY.replace("param", PARAM_CLASS), String.valueOf(roomClass.getValue()));
    }

    @Override
    public List<RoomType> getAll() throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_ALL);
    }

    @Override
    public void update(RoomType obj) throws JDBCException {
        JDBCManager.updateRequest(SQL.ROOM_TYPE_UPDATE,
                String.valueOf(obj.getCapacity()), obj.getBedsType(),
                String.valueOf(obj.getRoomClass().getValue()),
                obj.getDescription(),
                String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(DELETE, String.valueOf(id));
    }
}
