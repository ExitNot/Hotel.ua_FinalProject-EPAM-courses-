package com.epam.courses.java.final_project.dao.impl.jdbc;

/**
 * Exception during creating connection with DB or executing sql request in JDBC package
 *
 * @author Kostiantyn Kolchenko
 */
public class JDBCException extends Exception {
    public JDBCException() {
        super();
    }

    public JDBCException(String message) {
        super(message);
    }

    public JDBCException(String message, Throwable cause) {
        super(message + ":" + System.lineSeparator() + cause);
    }

    public JDBCException(Throwable cause) {
        super(cause);
    }
}
