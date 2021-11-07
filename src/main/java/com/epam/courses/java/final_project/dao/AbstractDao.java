package com.epam.courses.java.final_project.dao;

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

    void create(T obj);

    Optional<T> getById(long id);

    List<T> getAll();

    void update(T obj);

    void delete(long id);

    T createEntity(ResultSet rs) throws SQLException;
}
