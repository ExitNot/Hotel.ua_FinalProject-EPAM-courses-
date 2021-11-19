package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Image;

import java.util.List;

public interface ImageDao extends AbstractDao<Image>{

    List<Image> getByRoomType(long id) throws JDBCException;
}
