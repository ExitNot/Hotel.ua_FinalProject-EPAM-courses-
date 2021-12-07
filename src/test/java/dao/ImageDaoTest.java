package dao;

import com.epam.courses.java.final_project.dao.impl.ImageDaoImpl;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import com.epam.courses.java.final_project.model.Image;
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

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class ImageDaoTest {

    ResultSet rs;
    ImageDaoImpl dao = new ImageDaoImpl();
    private static final Logger log = LogManager.getLogger(ImageDaoTest.class);

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
            when(rs.getString(anyString())).thenReturn("path");
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
    }

    private Image generateEntity(long id) {
        return new Image(
                id, "path");
    }

    @Test
    void createEntityTest() {
        try {
            rsEntityCreationInit();
            Image Image = dao.createEntity(rs);
            Image expectedImage = generateEntity(1L);

            Assertions.assertEquals(expectedImage, Image);
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
        JDBCException e = Assertions.assertThrows(JDBCException.class, () -> {dao.getById(1L);});
        Assertions.assertEquals("Illegal operation", e.getMessage());
    }


    @Test
    void getAll() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getString(anyString())).thenReturn("path");
            List<Image> list = dao.getAll();
            List<Image> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(1L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void getByRoomTypeTest() {
        try {
            when(rs.next()).thenReturn(true, true, false);
            when(rs.getLong(anyString())).thenReturn(1L);
            when(rs.getString(anyString())).thenReturn("path");
            List<Image> list = dao.getByRoomType(1L);
            List<Image> expectedList = List.of(
                    generateEntity(1L),
                    generateEntity(1L)
            );

            Assertions.assertEquals(expectedList, list);
        } catch (JDBCException | SQLException e) {
            log.error("Test error", e);
        }
    }

    @Test
    void updateTest() {
        JDBCException e = Assertions.assertThrows(JDBCException.class, () -> {dao.update(generateEntity(1L));});
        Assertions.assertEquals("Illegal operation", e.getMessage());
    }

    @Test
    void deleteTest() {
        JDBCException e = Assertions.assertThrows(JDBCException.class, () -> {dao.delete(1L);});
        Assertions.assertEquals("Illegal operation", e.getMessage());
    }
}
