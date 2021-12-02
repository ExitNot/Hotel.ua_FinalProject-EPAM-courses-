package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.UserDao;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import static com.epam.courses.java.final_project.util.constant.Constant.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.getInstance().setTableName(SQL.SELECT_BY, TABLE_USER);
        SELECT_ALL = JDBCManager.getInstance().setTableName(SQL.SELECT_ALL, TABLE_USER);
        DELETE = JDBCManager.getInstance().setTableName(SQL.DELETE, TABLE_USER);
    }

    @Override
    public User createEntity(ResultSet rs) throws SQLException {
        return new User(rs.getLong(PARAM_ID),
                rs.getString(PARAM_EMAIL),
                rs.getString(PARAM_PWD),
                rs.getString(PARAM_NAME),
                rs.getString(PARAM_SURNAME),
                rs.getString(PARAM_PHONE_NUMBER),
                User.Role.getRole(rs.getInt(PARAM_ROLE)));
    }

    @Override
    public long create(User obj) throws JDBCException {
        long id = JDBCManager.getInstance().updateRequest(SQL.USER_INSERT,
                obj.getEmail(), obj.getPassword(),
                obj.getName(), obj.getSurname(),
                obj.getPhoneNumber(),
                String.valueOf(obj.getRole().getValue()));
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<User> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.getInstance().selectOneRequest(this, SELECT_BY.replace("param",  PARAM_ID), String.valueOf(id)));
    }

    @Override
    public Optional<User> getByEmail(String email) throws JDBCException {
        return Optional.ofNullable(JDBCManager.getInstance().selectOneRequest(this, SELECT_BY.replace("param",  PARAM_EMAIL), email));
    }

    @Override
    public List<User> getAll() throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_ALL);
    }

    @Override
    public List<User> getUsersByRole(User.Role role) throws JDBCException {
        return JDBCManager.getInstance().selectRequest(this, SELECT_BY, PARAM_ROLE, String.valueOf(role.getValue()));
    }

    @Override
    public long update(User obj) throws JDBCException {
        return JDBCManager.getInstance().updateRequest(SQL.USER_UPDATE,
                obj.getEmail(), obj.getPassword(),
                obj.getName(), obj.getSurname(), obj.getPhoneNumber(),
                String.valueOf(obj.getRole().getValue()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.getInstance().updateRequest(DELETE, String.valueOf(id));
    }
}
