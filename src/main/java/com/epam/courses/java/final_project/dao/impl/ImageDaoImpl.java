package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.ImageDao;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Image;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.*;
import static com.epam.courses.java.final_project.util.constant.Constant.TABLE_IMG;

/**
 * Image DAO implementation for PostgreSQL db.
 *
 * @author Kostiantyn Kolchenko
 */
public class ImageDaoImpl implements ImageDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.getInstance().setTableName(SQL.SELECT_BY, TABLE_IMG);
        SELECT_ALL = JDBCManager.getInstance().setTableName(SQL.SELECT_ALL, TABLE_IMG);
        DELETE = JDBCManager.getInstance().setTableName(SQL.DELETE, TABLE_IMG);
    }

    @Override
    public Image createEntity(ResultSet rs) throws SQLException {
        return new Image(
                rs.getLong(PARAM_ROOM_TYPE_ID),
                rs.getString(PARAM_IMG_PATH)
        );
    }

    @Override
    public long create(Image obj) throws JDBCException {
        return JDBCManager.getInstance().updateRequest(SQL.IMG_INSERT,
                String.valueOf(obj.getRoomTypeId()), obj.getPath());
    }

    @Override
    public Optional<Image> getById(long id) throws JDBCException {
        throw new JDBCException("Illegal operation");
    }

    @Override
    public List<Image> getByRoomType(long id) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this,
                SELECT_BY.replace("param", PARAM_ROOM_TYPE_ID), String.valueOf(id));
    }

    @Override
    public List<Image> getAll() throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_ALL);
    }

    @Override
    public long update(Image obj) throws JDBCException {
        throw new JDBCException("Illegal operation");
    }

    @Override
    public void delete(long id) throws JDBCException {
        throw new JDBCException("Illegal operation");
    }
}
