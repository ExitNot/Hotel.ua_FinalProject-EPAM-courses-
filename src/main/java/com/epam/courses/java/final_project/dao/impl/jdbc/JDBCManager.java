package com.epam.courses.java.final_project.dao.impl.jdbc;

import com.epam.courses.java.final_project.dao.AbstractDao;
import static com.epam.courses.java.final_project.util.Constant.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCManager {

    private static Connection conn;
    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    public static <T> T selectOneRequest(AbstractDao<T> dao, String sql, String... params) throws JDBCException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        T out = null;
        try {
            ps = createStatement(sql, params);
            log.trace(ps);
            rs = ps.executeQuery();
            if (rs.next())
                out = dao.createEntity(rs);
        } catch (SQLException e) {
            throw new JDBCException("Failed selectOne sql request:\n" + e);
        } finally {
            close(ps, rs);
        }
        return out;
    }

    public static <T> List<T> selectRequest(AbstractDao<T> dao, String sql, String... params) throws JDBCException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<T> list = new ArrayList<>();
        try {
            ps = createStatement(sql, params);
            log.trace(ps);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(dao.createEntity(rs));
            }
        } catch (SQLException e) {
            throw new JDBCException("Failed select sql request:\n" + e);
        } finally {
            close(ps, rs);
        }
        return list;
    }

    public static int updateRequest(String sql, String... params) throws JDBCException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int newId = 0;
        try {
            ps = createStatement(sql, params);
            log.trace(ps);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next())
                newId = rs.getInt(1);
            else
                throw new JDBCException("ResultSet is empty");
        } catch (SQLException e) {
            throw new JDBCException("Failed update sql request", e);
        } finally {
            close(ps, rs);
        }
        return newId;
    }

    private static PreparedStatement createStatement(String sql, String... params) throws SQLException {
        TCConnectionPool cp = TCConnectionPool.getInstance();
//        ConnectionPool cp = ConnectionPool.getInstance();
        conn = cp.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        setStatementParams(ps, params);
        return ps;
    }

    private static void setStatementParams(PreparedStatement ps, String... params) throws SQLException {
        for (int i = 0; i < params.length; i++){
            if (params[i].matches("\\d{4}-\\d{2}-\\d{2}"))
                ps.setDate(i + 1, Date.valueOf(params[i]));
            else if (params[i].matches("\\d+"))
                ps.setInt(i + 1, Integer.parseInt(params[i]));
            else if (params[i].matches("\\d+.\\d+"))
                ps.setDouble(i + 1, Double.parseDouble(params[i]));
            else
                ps.setString(i + 1, params[i]);
        }
    }

    public static String setTableName(String sql, String tableName) {
        return sql.replace("table", tableName);
    }

    private static void close(PreparedStatement ps, ResultSet rs) throws JDBCException {
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
            if (conn != null)
                conn.close();
            conn = null;
        } catch (SQLException e) {
            throw new JDBCException("Failed connection closing:\n" + e);
        }
    }

}
