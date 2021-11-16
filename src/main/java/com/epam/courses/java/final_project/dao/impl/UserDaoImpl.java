package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.UserDao;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import static com.epam.courses.java.final_project.util.Constant.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static final String SELECT_BY;
    private static final String SELECT_ALL;
    private static final String DELETE;

    static {
        SELECT_BY = JDBCManager.setTableName(SQL.SELECT_BY, TABLE_USER);
        SELECT_ALL = JDBCManager.setTableName(SQL.SELECT_ALL, TABLE_USER);
        DELETE = JDBCManager.setTableName(SQL.DELETE, TABLE_USER);
    }

    @Override
    public User createEntity(ResultSet rs) throws SQLException {
        return new User(rs.getLong(PARAM_ID),
                rs.getString(PARAM_LOGIN),
                rs.getString(PARAM_PWD),
                rs.getString(PARAM_NAME),
                rs.getString(PARAM_SURNAME),
                rs.getString(PARAM_PHONE_NUMBER),
                rs.getString(PARAM_EMAIL),
                User.Role.getRole(rs.getInt(PARAM_ROLE)));
    }

    @Override
    public long create(User obj) throws JDBCException {
        long id = JDBCManager.updateRequest(SQL.USER_INSERT, obj.getLogin(), obj.getPassword(),
                obj.getName(), obj.getSurname(),
                obj.getPhoneNumber(), obj.getEmail(),
                String.valueOf(obj.getRole().getValue()));
        obj.setId(id);
        return id;
    }

    @Override
    public Optional<User> getById(long id) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SELECT_BY.replace("param",  PARAM_ID), String.valueOf(id)));
    }

    @Override
    public Optional<User> getByLogin(String login) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SELECT_BY.replace("param",  PARAM_LOGIN), login));
    }

    @Override
    public Optional<User> getByEmail(String email) throws JDBCException {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SELECT_BY.replace("param",  PARAM_EMAIL), email));
    }

    @Override
    public List<User> getAll() throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_ALL);
    }

    @Override
public List<User> getUsersByRole(User.Role role) throws JDBCException {
        return JDBCManager.selectRequest(this, SELECT_BY, PARAM_ROLE, String.valueOf(role.getValue()));
    }

    @Override
    public void update(User obj) throws JDBCException {
        JDBCManager.updateRequest(SQL.USER_UPDATE, obj.getLogin(),
                obj.getPassword(), obj.getPhoneNumber(), obj.getEmail(),
                String.valueOf(obj.getRole().getValue()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) throws JDBCException {
        JDBCManager.updateRequest(DELETE, String.valueOf(id));
    }
}
