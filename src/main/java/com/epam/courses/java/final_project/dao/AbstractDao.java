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
 * */
public interface AbstractDao<T> {

    void create(T obj) throws JDBCException;

    Optional<T> getById(long id);

    List<T> getAll();

    void update(T obj) throws JDBCException;

    void delete(long id) throws JDBCException;

    T createEntity(ResultSet rs) throws SQLException;
}
