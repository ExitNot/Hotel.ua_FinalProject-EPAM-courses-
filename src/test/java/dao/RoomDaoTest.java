package dao;

import com.epam.courses.java.final_project.dao.impl.RoomDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Room;
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

public class RoomDaoTest {

    ResultSet rs;
    RoomDaoImpl dao = new RoomDaoImpl();
    private static final Logger log = LogManager.getLogger(RoomDaoTest.class);

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
            when(rs.getLong(anyString())).thenReturn(1L, 1L);
            when(rs.getInt(anyString())).thenReturn(101, 1);
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
    }

    private Room generateEntity(long id) {
        return new Room(id, 100 + (int) id, 1, 1);
    }

    @Test
    void createEntityTest() {
        try {
            rsEntityCreationInit();
            Room room = dao.createEntity(rs);
            Room expectedRoom = generateEntity(1L);

            Assertions.assertEquals(expectedRoom, room);
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
            Optional<Room> oRoom = dao.getById(1L);
            Room expectedRoom = generateEntity(1L);

            if (oRoom.isEmpty())
                throw new JDBCException("Room not found");
            oRoom.ifPresent(room -> Assertions.assertEquals(expectedRoom, room));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByRoomNumberTest() {
        try {
            rsEntityCreationInit();
            Optional<Room> oRoom = dao.getByRoomNum(1);
            Room expectedRoom = generateEntity(1L);

            if (oRoom.isEmpty())
                throw new JDBCException("Room not found");
            oRoom.ifPresent(Room -> Assertions.assertEquals(expectedRoom, Room));
        } catch (JDBCException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getAll() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 2L, 1L);
            when(rs.getInt(anyString())).thenReturn(101, 1, 102, 1);
            List<Room> list = dao.getAll();
            List<Room> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(2L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getRoomByRoleTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L, 1L, 2L, 1L);
            when(rs.getInt(anyString())).thenReturn(101, 1, 102, 1);
            List<Room> list = dao.getRoomsByFloor(1);
            List<Room> expectedList = List.of(
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
            Room Room = generateEntity(1L);

            long id = dao.update(Room);

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
