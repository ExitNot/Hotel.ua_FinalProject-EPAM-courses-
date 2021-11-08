package com.epam.courses.java.final_project.dao.impl.jdbc;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConnectionPool {

    private String url;
    private String user;
    private String password;
    private Queue<Connection> connectionPool;
    private List<Connection> usedConnectionPool;
    private int poolSize;
    private static final ConnectionPool INSTANCE = new ConnectionPool();
    private final Logger logger = LogManager.getLogger("ConnectionPool");

    private ConnectionPool() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("src/main/resources/app.properties"));

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            poolSize = Integer.parseInt(prop.getProperty("connection.pool.size"));
            System.out.println("Logger have to be here");
            logger.info("Connection pool size have been set to " + poolSize);  // RM ------------
            connectionPool = new LinkedBlockingQueue<>();
            usedConnectionPool = new ArrayList<>();
            for (int i = 0; i < poolSize; i++) {
                connectionPool.add(DriverManager.getConnection(url, user, password));
            }
        } catch (SQLException | IOException e) {
            logger.error("Error during initialization of connection pool", e);
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
