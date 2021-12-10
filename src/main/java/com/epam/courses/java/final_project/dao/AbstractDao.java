package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interface defines an abstract API that performs CRUD operations for any entity type.
 *
 * @author Kostiantyn Kolchenko
 */
public interface AbstractDao<T> {

    long create(T obj) throws JDBCException;

    Optional<T> getById(long id) throws JDBCException;

    List<T> getAll() throws JDBCException;

    long update(T obj) throws JDBCException;

    void delete(long id) throws JDBCException;

    T createEntity(ResultSet rs) throws SQLException;
}
