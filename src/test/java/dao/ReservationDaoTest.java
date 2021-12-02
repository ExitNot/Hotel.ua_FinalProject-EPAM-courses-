package dao;

import com.epam.courses.java.final_project.dao.impl.ReservationDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Reservation;
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

public class ReservationDaoTest {

    ResultSet rs;
    ReservationDaoImpl dao = new ReservationDaoImpl();
    private static final Logger log = LogManager.getLogger(ReservationDaoTest.class);

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
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1);
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
    }

    private Reservation generateEntity(long id) {
        return new Reservation(
                id, 1L, 1L,
                Util.getToday(), Util.getToday(), 1);
    }

    @Test
    void createEntityTest() {
        try {
            rsEntityCreationInit();
            Reservation Reservation = dao.createEntity(rs);
            Reservation expectedReservation = generateEntity(1L);

            Assertions.assertEquals(expectedReservation, Reservation);
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
            Optional<Reservation> oReservation = dao.getById(1L);
            Reservation expectedReservation = generateEntity(1L);

            if (oReservation.isEmpty())
                throw new JDBCException("Reservation not found");
            oReservation.ifPresent(Reservation -> Assertions.assertEquals(expectedReservation, Reservation));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }


    @Test
    void getAll() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1);
            List<Reservation> list = dao.getAll();
            List<Reservation> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getUsersReservationsTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1);
            List<Reservation> list = dao.getUserReservations(1L);
            List<Reservation> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getRoomReservationsTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1);
            List<Reservation> list = dao.getRoomReservations(1L);
            List<Reservation> expectedList = List.of(
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
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 1L, 2L, 1L, 1L);
            when(rs.getDate(anyString())).thenReturn(Util.getToday());
            when(rs.getInt(anyString())).thenReturn(1);
            List<Reservation> list = dao.getByDate(Util.getToday(), Util.getToday());
            List<Reservation> expectedList = List.of(
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
            Reservation Reservation = generateEntity(1L);

            long id = dao.update(Reservation);

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
