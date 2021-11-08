package com.epam.courses.java.final_project.dao.impl.jdbc;

import com.epam.courses.java.final_project.dao.AbstractDao;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCManager {

    private static Connection conn;
    private static final Logger logger = LogManager.getLogger();

    public static <T> T selectOneRequest(AbstractDao<T> dao, String sql, String... params){
        try (PreparedStatement ps = createStatement(sql, params)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return dao.createEntity(rs);
            else
                return null;
        } catch (SQLException e) {
//            todo throw my exception
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static <T> List<T> selectRequest(AbstractDao<T> dao, String sql, String... params){
        List<T> list = new ArrayList<>();
        try (PreparedStatement ps = createStatement(sql, params)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next())
                list.add(dao.createEntity(rs));
            return list;
        } catch (SQLException e) {
//            todo throw my exception
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static int updateRequest(String sql, String... params) throws JDBCException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int newId = 0;
        try {
            ps = createStatement(sql, params);
            ps.executeUpdate();
            rs = ps.getGeneratedKeys();
            if (rs.next())
                newId = rs.getInt(1);
            else
                throw new JDBCException("ResultSet is empty");
        } catch (SQLException e) {
            logger.error("Exception during executing request", e);
            throw new JDBCException("Failed sql request", e);
        } finally {
            close(ps, rs);
        }
        return newId;
    }

    private static PreparedStatement createStatement(String sql, String... params) throws SQLException {
        ConnectionPool cp = ConnectionPool.getInstance();
        conn = cp.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        setStatementParams(ps, params);
        return ps;
    }

    private static void close(PreparedStatement ps, ResultSet rs) {
        try {
            if (ps != null)
                ps.close();
            if (rs != null)
                rs.close();
            ConnectionPool.getInstance().releaseConnection(conn);
            conn = null;
        } catch (SQLException e) {
            logger.warn("Failed connection closing", e);
        }
    }

    private static void setStatementParams(PreparedStatement ps, String... params) throws SQLException {
        for (int i = 0; i < params.length; i++){
            if (params[i].matches("\\d{4}-\\d{2}-\\d{2}"))
                ps.setDate(i + 1, Date.valueOf(params[i]));
            else if (params[i].matches("\\d+"))
                ps.setInt(i + 1, Integer.parseInt(params[i]));
            else
                ps.setString(i + 1, params[i]);
        }
    }

}
