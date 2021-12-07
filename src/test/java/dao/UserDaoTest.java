package dao;

import com.epam.courses.java.final_project.dao.impl.UserDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import util.TestUtil;

import java.sql.*;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserDaoTest {

    ResultSet rs;
    UserDaoImpl dao = new UserDaoImpl();
    private static final Logger log = LogManager.getLogger(UserDaoTest.class);

    @BeforeEach
    void setUp() {
        rs = TestUtil.daoSetUp();
    }

    @AfterEach
    void clean() {
        JDBCManager.getInstance().deleteTestConn();
    }

    private void rsEntityCreationInit() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getInt(anyString())).thenReturn(User.Role.Customer.getValue());
            when(rs.getString(anyString())).thenReturn("user@gmail.com", "pwd", "User", "Userov", "1234");
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
    }

    private User generateEntity(long id) {
        return new User(
                id, "user@gmail.com", "pwd",
                "User", "Userov", "1234", User.Role.Customer);
    }

    @Test
    void createEntityTest() {
        try {
            rsEntityCreationInit();
            User user = dao.createEntity(rs);
            User expectedUser = generateEntity(1L);

            Assertions.assertEquals(expectedUser, user);
        } catch (SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void createTest() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(1)).thenReturn(1L);

            long id = dao.create(generateEntity(1L));

            Assertions.assertEquals(1L, id);
        } catch (SQLException | JDBCException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByIdTest() {
        try {
            rsEntityCreationInit();
            Optional<User> oUser = dao.getById(1L);
            User expectedUser = generateEntity(1L);

            if (oUser.isEmpty())
                throw new JDBCException("User not found");
            oUser.ifPresent(user -> Assertions.assertEquals(expectedUser, user));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByEmailTest() {
        try {
            rsEntityCreationInit();
            Optional<User> oUser = dao.getByEmail("user@gmail.com");
            User expectedUser = generateEntity(1L);

            if (oUser.isEmpty())
                throw new JDBCException("User not found");
            oUser.ifPresent(user -> Assertions.assertEquals(expectedUser, user));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getAll() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getInt(anyString())).thenReturn(User.Role.Customer.getValue(), User.Role.Customer.getValue());
            when(rs.getString(anyString())).thenReturn("user@gmail.com", "pwd", "User", "Userov", "1234",
                    "user@gmail.com", "pwd", "User", "Userov", "1234");
            List<User> list = dao.getAll();
            List<User> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getUserByRoleTest() {
        try {
            when(rs.next()).thenReturn(true, false);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getInt(anyString())).thenReturn(User.Role.Customer.getValue());
            when(rs.getString(anyString())).thenReturn("user@gmail.com", "pwd", "User", "Userov", "1234");
            List<User> list = dao.getUsersByRole(User.Role.Customer);
            List<User> expectedList = List.of(
                    generateEntity(1L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void updateTest() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(1)).thenReturn(1L);
            User user = generateEntity(1L);

            long id = dao.update(user);

            Assertions.assertEquals(1L, id);
        } catch (SQLException | JDBCException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void deleteTest() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(1)).thenReturn(1L);

            dao.delete(1L);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }
}