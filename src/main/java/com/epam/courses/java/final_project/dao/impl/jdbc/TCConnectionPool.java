package com.epam.courses.java.final_project.dao.impl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

public class TCConnectionPool {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);
    private static final TCConnectionPool INSTANCE = new TCConnectionPool();
    private DataSource ds;

    private TCConnectionPool() {
        InitialContext ctx = null;
        try {
            ctx = new InitialContext();
            Context envCtx = (Context) ctx.lookup("java:comp/env/");
            ds = (DataSource) envCtx.lookup("jdbc/postgreSQL");
        } catch (NamingException e) {
            log.error("Context error", e);
        }
    }

    public static TCConnectionPool getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        Connection conn = null;
        try {
            if (ds != null) {
                conn = ds.getConnection();
            } else {
                throw new JDBCException("Datasource have not been found");
            }
        } catch (SQLException e) {
            log.error("Connection pool can not create connection", e);
        } catch (JDBCException e) {
            log.error("Datasource have not been found");
        }
        return conn;
    }
}
