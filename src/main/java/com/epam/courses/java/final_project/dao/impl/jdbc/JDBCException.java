package com.epam.courses.java.final_project.dao.impl.jdbc;

/**
 * Exception during creating or executing sql request from JDBC Manager
 *
 * @author Kostiantyn Kolchenko
 * */
public class JDBCException extends Exception{
    public JDBCException() {
        super();
    }

    public JDBCException(String message) {
        super(message);
    }

    public JDBCException(String message, Throwable cause) {
        super(message, cause);
    }

    public JDBCException(Throwable cause) {
        super(cause);
    }
}
