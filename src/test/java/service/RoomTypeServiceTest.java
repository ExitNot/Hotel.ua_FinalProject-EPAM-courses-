package service;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Image;
import com.epam.courses.java.final_project.model.RoomType;
import com.epam.courses.java.final_project.service.RoomTypeService;
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

public class RoomTypeServiceTest {

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
            when(rs.getString(anyString())).thenReturn("1D", "sample");

            Optional<RoomType> oRoomType = RoomTypeService.getById(1L);
            RoomType expected = new RoomType(1L, 1, "1D", RoomType.RoomClass.Standard, "sample");

            oRoomType.ifPresent(RoomType -> Assertions.assertEquals(expected, RoomType));
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getAllTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getString(anyString())).thenReturn("1D", "sample", "1D", "sample");

            List<RoomType> list = RoomTypeService.getAll();
            List<RoomType> expectedList = List.of(
                    new RoomType(1L, 1, "1D", RoomType.RoomClass.Standard, "sample"),
                    new RoomType(2L, 1, "1D", RoomType.RoomClass.Standard, "sample")
            );

            Assertions.assertEquals(expectedList, list);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getByCapacityTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 2L);
            when(rs.getInt(anyString())).thenReturn(1);
            when(rs.getString(anyString())).thenReturn("1D", "sample", "1D", "sample");

            List<RoomType> list = RoomTypeService.getByCapacity(1);
            List<RoomType> expectedList = List.of(
                    new RoomType(1L, 1, "1D", RoomType.RoomClass.Standard, "sample"),
                    new RoomType(2L, 1, "1D", RoomType.RoomClass.Standard, "sample")
            );

            Assertions.assertEquals(expectedList, list);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }

    @Test
    void getImgTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getString(anyString())).thenReturn("path");

            List<Image> list = RoomTypeService.getImg(1L);
            List<Image> expectedList = List.of(
                    new Image(1L, "path"),
                    new Image(1L, "path")
            );

            Assertions.assertEquals(expectedList, list);
        } catch (SQLException e) {
            log.error("Failed ResultSet mocking", e);
        } catch (JDBCException e) {
            log.error(e);
            Assertions.fail();
        }
    }
}
