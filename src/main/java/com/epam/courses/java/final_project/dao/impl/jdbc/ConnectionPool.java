package com.epam.courses.java.final_project.dao.impl.jdbc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

public class ConnectionPool {

    private String url;
    private String user;
    private String password;
    private Queue<Connection> connectionPool;
    private List<Connection> usedConnectionPool;
    private int poolSize;
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    private ConnectionPool() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("src/main/resources/app.properties"));

            url = prop.getProperty("db.testUrl");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            poolSize = Integer.parseInt(prop.getProperty("connection.pool.size"));
            connectionPool = new ConcurrentLinkedQueue<>();  // todo can be better
            usedConnectionPool = new ArrayList<>();
            for (int i = 0; i < poolSize; i++) {
                connectionPool.add(DriverManager.getConnection(url, user, password));
            }
        } catch (IOException | SQLException e) {
            log.error(e.getMessage());
        }
    }

    public static ConnectionPool getInstance() {
        return INSTANCE;
    }

    public Connection getConnection() {
        Connection connection = connectionPool.poll();
        usedConnectionPool.add(connection);
        return connection;
    }

    public void releaseConnection(Connection connection) throws SQLException {
        connectionPool.add(DriverManager.getConnection(url, user, password));
        usedConnectionPool.remove(connection);
        connection.close();
    }
}