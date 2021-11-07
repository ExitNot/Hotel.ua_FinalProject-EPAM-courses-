package com.epam.courses.java.final_project.dao.impl.jdbc;

import com.epam.courses.java.final_project.dao.AbstractDao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JDBCManager {

    private static final ConnectionPool cp = ConnectionPool.getInstance();

    public static <T> T selectOneRequest(AbstractDao<T> dao, String sql, String... params){
        try (PreparedStatement ps = createStatement(sql, params)) {
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return dao.createEntity(rs);
            else
                return null;
        } catch (SQLException e) {
//            todo throw my exception
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
            return null;
        }
    }

    public static int updateRequest(String sql, String... params){  // returns id
        try (PreparedStatement ps = createStatement(sql, params)) {
//            ps.executeUpdate();
            System.out.println("ps.executed" + ps.executeUpdate());
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
                return rs.getInt(1);
            else
                return 0;
        } catch (SQLException e) {
//            todo throw my exception
            System.err.println(e.getMessage());
            return 0;
        }
    }

    private static PreparedStatement createStatement(String sql, String... params) throws SQLException {
        System.out.println("creating statement...");
        Connection conn = cp.getConnection();
//        System.out.println("connection is null? " + (conn == null) + "" + conn.isClosed());
        PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        setStatementParams(ps, params);
        return ps;
    }

    private static void setStatementParams(PreparedStatement ps, String... params) throws SQLException {
        for (int i = 0; i < params.length; i++){
            if (params[i].matches("\\d+"))
                ps.setInt(i + 1, Integer.parseInt(params[i]));
            else
                ps.setString(i + 1, params[i]);
        }
    }

}
