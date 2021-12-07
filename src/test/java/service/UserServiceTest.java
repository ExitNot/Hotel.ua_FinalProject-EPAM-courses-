package service;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.User;
import com.epam.courses.java.final_project.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.TestUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    ResultSet rs;

    @BeforeEach
    void setUp() {
        rs = TestUtil.daoSetUp();
    }

    @AfterEach
    void clean() {
        JDBCManager.getInstance().deleteTestConn();
    }

    @Test
    void getByIdTest() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getString(anyString())).thenReturn("user@mail.com", "pwd",
                    "User", "Userov", "1234");

            Optional<User> oUser = UserService.getById(1L);
            User expected = new User(1L, "user@mail.com", "pwd",
                    "User", "Userov", "1234", User.Role.Customer);

            oUser.ifPresent(User -> Assertions.assertEquals(expected, User));
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByEmailTest() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getString(anyString())).thenReturn("user@mail.com", "pwd",
                    "User", "Userov", "1234");

            Optional<User> oUser = UserService.getByEmail("user@mail.com");
            User expected = new User(1L, "user@mail.com", "pwd",
                    "User", "Userov", "1234", User.Role.Customer);

            oUser.ifPresent(User -> Assertions.assertEquals(expected, User));
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }
}
