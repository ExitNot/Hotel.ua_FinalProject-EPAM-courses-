package service;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RequestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import util.TestUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RequestServiceTest {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    private static Date from;
    private static Date to;
    private static Date twoDaysAgo;
    ResultSet rs;

    @BeforeAll
    static void beforeAll() {
        Calendar c  = Calendar.getInstance();
        from = new Date(c.getTimeInMillis());
        c.add(Calendar.DAY_OF_YEAR, 1);
        to = new Date(c.getTimeInMillis());
        c.roll(Calendar.DAY_OF_YEAR, false);
        c.roll(Calendar.DAY_OF_YEAR, false);
        c.roll(Calendar.DAY_OF_YEAR, false);
        c.roll(Calendar.DAY_OF_YEAR, false);
        c.roll(Calendar.DAY_OF_YEAR, false);
        c.roll(Calendar.DAY_OF_YEAR, false);
        twoDaysAgo = new Date(c.getTimeInMillis());
    }

    private void baseListRsSetUp(){
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(from, to, from, from, to, from);
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getDouble(anyString())).thenReturn(0.0);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        }
    }

    private void baseListRsSetUpWithExpiredDate(){
        try {
            when(rs.next()).thenReturn(true, true, false, true);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L, 2L);
            when(rs.getDate(anyString())).thenReturn(from, to, from, from, to, twoDaysAgo);
            when(rs.getInt(anyString())).thenReturn(1, 1, 1, 1, 1, 1, 1, 3);
            when(rs.getDouble(anyString())).thenReturn(0.0);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        }
    }

    @BeforeEach
    void setUp() {
        rs = TestUtil.daoSetUp();
    }

    @AfterEach
    void clean() {
        JDBCManager.getInstance().deleteTestConn();
    }

    @Test
    void getByDateTest() {
        baseListRsSetUp();
        try {
            List<Request> list = RequestService.getByDate(from, to);
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0),
                    new Request(2L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByDateWithExpiredDateTest() {
        baseListRsSetUpWithExpiredDate();

        try {
            List<Request> list = RequestService.getByDate(from, to);
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByUserIdTest() {
        baseListRsSetUp();

        try {
            List<Request> list = RequestService.getByUserId(1L);
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0),
                    new Request(2L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByUserIdWithExpiredDateTest() {
        baseListRsSetUpWithExpiredDate();

        try {
            List<Request> list = RequestService.getByUserId(1L);
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByStatusTest() {
        baseListRsSetUp();

        try {
            List<Request> list = RequestService.getByStatus(Request.Status.ManagerResponse);
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0),
                    new Request(2L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByStatusWithExpiredDateTest() {
        baseListRsSetUpWithExpiredDate();

        try {
            List<Request> list = RequestService.getByStatus(Request.Status.ManagerResponse);
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getAllTest() {
        baseListRsSetUp();

        try {
            List<Request> list = RequestService.getAll();
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0),
                    new Request(2L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getAllWithExpiredDateTest() {
        baseListRsSetUpWithExpiredDate();

        try {
            List<Request> list = RequestService.getAll();
            List<Request> expectedList = List.of(
                    new Request(1L, 1L, 1L, from, to, from, 1, 1,
                            RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByIdTest() {
        baseListRsSetUp();

        try {
            Optional<Request> oRequest = RequestService.getById(1L);
            Request expected = new Request(1L, 1L, 1L, from, to, from, 1, 1,
                    RoomType.RoomClass.Standard, Request.Status.ManagerResponse, 0.0);

            oRequest.ifPresent(request -> Assertions.assertEquals(expected, request));
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByIdWithExpiredDateTest() {
        try {
            when(rs.next()).thenReturn(true, true);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(from, to, twoDaysAgo);
            when(rs.getInt(anyString())).thenReturn(1, 1, 1, 3);
            when(rs.getDouble(anyString())).thenReturn(0.0);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        }

        try {
            Optional<Request> oRequest = RequestService.getById(1L);

            if (oRequest.isPresent()){  // ifPresent not works here
                Assertions.fail();
            }
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }
}
