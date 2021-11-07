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
import java.util.concurrent.ConcurrentLinkedQueue;
//import org.apache.logging.log4j.LogManager;

public class ConnectionPool {

    private String url;
    private String user;
    private String password;
    private Queue<Connection> connectionPool;
    private List<Connection> usedConnectionPool;
    private int poolSize;
    private static final ConnectionPool INSTANCE = new ConnectionPool();
//    private Logger logger = LogManager.getLogManager();  todo Logger

    private ConnectionPool() {
        Properties prop = new Properties();
        try {
            prop.load(new FileReader("src/main/resources/app.properties"));

            url = prop.getProperty("db.url");
            user = prop.getProperty("db.user");
            password = prop.getProperty("db.password");
            poolSize = Integer.parseInt(prop.getProperty("connection.pool.size"));
            connectionPool = new ConcurrentLinkedQueue<>();  // todo can be better
            usedConnectionPool = new ArrayList<>();
            for (int i = 0; i < poolSize; i++) {
                connectionPool.add(DriverManager.getConnection(url, user, password));
            }
        } catch (IOException e) {
            System.err.println(e.getMessage()); // todo logg amd throw my exceptions
        } catch (SQLException e) {
            System.err.println(e.getMessage());
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
        // todo re-open connection
        connectionPool.add(connection);
        usedConnectionPool.remove(connection);
        connection.close();
    }
}
