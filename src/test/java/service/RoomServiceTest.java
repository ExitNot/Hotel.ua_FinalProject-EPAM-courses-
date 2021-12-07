package service;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.service.RoomService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import util.TestUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class RoomServiceTest {

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
            when(rs.getInt(anyString())).thenReturn(101, 1);

            Optional<Room> oRoom = RoomService.getById(1L);
            Room expected = new Room(1L, 101, 1, 1L);

            oRoom.ifPresent(room -> Assertions.assertEquals(expected, room));
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
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 2L, 1L);
            when(rs.getInt(anyString())).thenReturn(101, 1, 102, 1);

            List<Room> list = RoomService.getAll();
            List<Room> expectedList = List.of(
                    new Room(1L, 101, 1, 1L),
                    new Room(2L, 102, 1, 1L)
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
