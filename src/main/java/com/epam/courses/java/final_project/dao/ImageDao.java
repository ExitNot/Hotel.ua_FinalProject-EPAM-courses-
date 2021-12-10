package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Image;
import com.epam.courses.java.final_project.model.RoomType;

import java.util.List;

/**
 * DAO interface for {@code Image}
 *
 * @author Kostiantyn Kolchenko
 * @see Image
 */
public interface ImageDao extends AbstractDao<Image>{

    List<Image> getByRoomType(long id) throws JDBCException;
}
