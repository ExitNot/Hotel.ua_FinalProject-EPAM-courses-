package com.epam.courses.java.final_project.dao.impl;

import com.epam.courses.java.final_project.dao.UserDao;
import com.epam.courses.java.final_project.dao.entity.User;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import static com.epam.courses.java.final_project.util.Constant.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private final String SQL_USER_INSERT = "INSERT INTO users (login, password, phone_number, email, role) VALUES (?, ?, ?, ?, ?);";
    private final String SQL_USER_UPDATE = "UPDATE users SET login = ?, password = ?, phone_number = ?, email = ?, role = ? WHERE id = ?";

    @Override
    public User createEntity(ResultSet rs) throws SQLException {
        return new User(rs.getLong(PARAM_ID),
                rs.getString(PARAM_LOGIN),
                rs.getString(PARAM_PWD),
                rs.getString(PARAM_PHONE_NUMBER),
                rs.getString(PARAM_EMAIL),
                User.Role.getRole(rs.getInt(PARAM_ROLE)));
    }

    @Override
    public void create(User obj) {
        System.out.println("create UDAOImpl");
        int id = JDBCManager.updateRequest(SQL_USER_INSERT, obj.getLogin(), obj.getPassword(),
                obj.getPhoneNumber(), obj.getEmail(),
                String.valueOf(obj.getRole().getValue()));
        obj.setId(id);
    }

    @Override
    public Optional<User> getById(long id) {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL_SELECT_BY, TABLE_USER, PARAM_ID, String.valueOf(id)));
    }

    @Override
    public Optional<User> getByLogin(String login) {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL_SELECT_BY, TABLE_USER, PARAM_LOGIN, login));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.ofNullable(JDBCManager.selectOneRequest(this, SQL_SELECT_BY, TABLE_USER, PARAM_EMAIL, email));
    }

    @Override
    public List<User> getAll() {
        return JDBCManager.selectRequest(this, SQL_SELECT_ALL, TABLE_USER);
    }

    @Override
    public List<User> getUsersByRole(User.Role role) {
        return JDBCManager.selectRequest(this, SQL_SELECT_BY, TABLE_USER, PARAM_ROLE, String.valueOf(role.getValue()));
    }

    @Override
    public void update(User obj) {
        JDBCManager.updateRequest(SQL_USER_UPDATE, obj.getLogin(),
                obj.getPassword(), obj.getPhoneNumber(), obj.getEmail(),
                String.valueOf(obj.getRole().getValue()), String.valueOf(obj.getId()));
    }

    @Override
    public void delete(long id) {
        JDBCManager.updateRequest(SQL_DELETE, TABLE_USER, String.valueOf(id));
    }
}
