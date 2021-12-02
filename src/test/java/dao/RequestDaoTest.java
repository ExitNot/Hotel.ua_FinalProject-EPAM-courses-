package dao;

import com.epam.courses.java.final_project.dao.impl.RequestDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Request;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.util.Util;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RequestDaoTest {

    ResultSet rs;
    RequestDaoImpl dao = new RequestDaoImpl();
    private static final Logger log = LogManager.getLogger(RequestDaoTest.class);

    @BeforeEach
    void setUp() {
        rs = TestUtil.setUp();
    }

    @AfterEach
    void clean() {
        JDBCManager.getInstance().deleteTestConn();
    }

    private void rsEntityCreationInit() {
        try {
            when(rs.next()).thenReturn(true);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getDouble(anyString())).thenReturn(0.0);
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
    }

    private Request generateEntity(long id) {
        return new Request(
                id, 1L, 1L,
                Util.getToday(),
                Util.getToday(),
                Util.getToday(),
                1, 1, RoomType.RoomClass.Standard,
                com.epam.courses.java.final_project.model.Request.Status.ManagerResponse, 0.0);
    }

    @Test
    void createEntityTest() {
        try {
            rsEntityCreationInit();
            Request Request = dao.createEntity(rs);
            Request expectedRequest = generateEntity(1L);

            Assertions.assertEquals(expectedRequest, Request);
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
            Optional<Request> oRequest = dao.getById(1L);
            Request expectedRequest = generateEntity(1L);

            if (oRequest.isEmpty())
                throw new JDBCException("Request not found");
            oRequest.ifPresent(Request -> Assertions.assertEquals(expectedRequest, Request));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }


    @Test
    void getAll() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1, 1, 1, 1, 1, 1, 1, 1);
            when(rs.getDouble(anyString())).thenReturn(0.0, 0.0);
            List<Request> list = dao.getAll();
            List<Request> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getUsersRequestsTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1, 1, 1, 1, 1, 1, 1, 1);
            when(rs.getDouble(anyString())).thenReturn(0.0, 0.0);
            List<Request> list = dao.getUserRequests(1L);
            List<Request> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getRequestsByStatusTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1, 1, 1, 1, 1, 1, 1, 1);
            when(rs.getDouble(anyString())).thenReturn(0.0, 0.0);
            List<Request> list = dao.getRequestsByStatus(Request.Status.ManagerResponse);
            List<Request> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByDateTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1, 1, 1, 1, 1, 1, 1, 1);
            when(rs.getDouble(anyString())).thenReturn(0.0, 0.0);
            List<Request> list = dao.getByDate(Util.getToday(), Util.getToday());
            List<Request> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
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
            Request Request = generateEntity(1L);

            long id = dao.update(Request);

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
