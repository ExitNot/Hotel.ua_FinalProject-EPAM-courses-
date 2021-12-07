package service;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Reservation;
import com.epam.courses.java.final_project.service.ReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import util.TestUtil;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ReservationServiceTest {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    private static Date from;
    private static Date to1;
    private static Date to2;
    ResultSet rs;

    @BeforeAll
    static void beforeAll() {
        Calendar c  = Calendar.getInstance();
        c.roll(Calendar.DAY_OF_YEAR, false);
        c.roll(Calendar.DAY_OF_YEAR, false);
        from = new Date(c.getTimeInMillis());
        c.add(Calendar.DAY_OF_YEAR, 1);
        to2 = new Date(c.getTimeInMillis());
        c.add(Calendar.DAY_OF_YEAR, 2);
        to1 = new Date(c.getTimeInMillis());
    }

    private void baseListRsSetUp(){
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(from, to1, from, to1);
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getDouble(anyString())).thenReturn(0.0);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        }
    }

    private void baseListRsSetUpWithExpiredDate(){
        try {
            when(rs.next()).thenReturn(true, true, false, true);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(from, to1, from, to2);
            when(rs.getInt(anyString())).thenReturn(1);
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
            List<Reservation> list = ReservationService.getByDate(from, to1);
            List<Reservation> expectedList = List.of(
                    new Reservation(1L, 1L, 1L, from, to1, 1),
                    new Reservation(2L, 1L, 1L, from, to1, 1)
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
            List<Reservation> list = ReservationService.getByDate(from, to1);
            List<Reservation> expectedList = List.of(
                    new Reservation(1L, 1L, 1L, from, to1, 1)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByUserTest() {
        baseListRsSetUp();
        try {
            List<Reservation> list = ReservationService.getByUser(1L);
            List<Reservation> expectedList = List.of(
                    new Reservation(1L, 1L, 1L, from, to1, 1),
                    new Reservation(2L, 1L, 1L, from, to1, 1)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByUserWithExpiredDateTest() {
        baseListRsSetUpWithExpiredDate();

        try {
            List<Reservation> list = ReservationService.getByUser(1L);
            List<Reservation> expectedList = List.of(
                    new Reservation(1L, 1L, 1L, from, to1, 1)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }


}
