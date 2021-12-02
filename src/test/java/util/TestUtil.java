package util;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TestUtil {

    private static final Logger log = LogManager.getLogger(TestUtil.class);

    public static ResultSet setUp() {
        ResultSet rs = null;
        try {
            Connection conn = mock(Connection.class);
            PreparedStatement ps = mock(PreparedStatement.class);
            rs = mock(ResultSet.class);
            when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(ps);
            when(ps.getGeneratedKeys()).thenReturn(rs);
            when(ps.executeQuery()).thenReturn(rs);
            JDBCManager.getInstance().setTestConn(conn);
        } catch (SQLException e) {
            log.error("Entity creation error", e);
        }
        return rs;
    }
}
