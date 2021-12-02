package dao;

import com.epam.courses.java.final_project.dao.impl.RoomTypeDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.RoomType;
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

public class RoomTypeDaoTest {

    ResultSet rs;
    RoomTypeDaoImpl dao = new RoomTypeDaoImpl();
    private static final Logger log = LogManager.getLogger(RoomTypeDaoTest.class);

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
            when(rs.getString(anyString())).thenReturn("1D", "sample");
            when(rs.getInt(anyString())).thenReturn(1);
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
    }

    private RoomType generateEntity(long id) {
        return new RoomType(
                id, 1, "1D", RoomType.RoomClass.Standard, "sample");
    }

    @Test
    void createEntityTest() {
        try {
            rsEntityCreationInit();
            RoomType RoomType = dao.createEntity(rs);
            RoomType expectedRoomType = generateEntity(1L);

            Assertions.assertEquals(expectedRoomType, RoomType);
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
            Optional<RoomType> oRoomType = dao.getById(1L);
            RoomType expectedRoomType = generateEntity(1L);

            if (oRoomType.isEmpty())
                throw new JDBCException("RoomType not found");
            oRoomType.ifPresent(RoomType -> Assertions.assertEquals(expectedRoomType, RoomType));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }


    @Test
    void getAll() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getString(anyString())).thenReturn("1D", "sample", "1D", "sample");
            when(rs.getInt(anyString())).thenReturn(1);
            List<RoomType> list = dao.getAll();
            List<RoomType> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByCapacityTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getString(anyString())).thenReturn("1D", "sample", "1D", "sample");
            when(rs.getInt(anyString())).thenReturn(1);
            List<RoomType> list = dao.getByCapacity(1);
            List<RoomType> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByClassTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getString(anyString())).thenReturn("1D", "sample", "1D", "sample");
            when(rs.getInt(anyString())).thenReturn(1 );
            List<RoomType> list = dao.getByClass(RoomType.RoomClass.Standard);
            List<RoomType> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByBedsTypeTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getString(anyString())).thenReturn("1D", "sample", "1D", "sample");
            when(rs.getInt(anyString())).thenReturn(1);
            List<RoomType> list = dao.getByBedsType("1D");
            List<RoomType> expectedList = List.of(
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
            RoomType RoomType = generateEntity(1L);

            long id = dao.update(RoomType);

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
