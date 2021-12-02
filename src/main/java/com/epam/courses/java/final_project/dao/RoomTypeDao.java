package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.RoomType;

import java.util.List;

public interface RoomTypeDao extends AbstractDao<RoomType>{

    List<RoomType> getByCapacity(int capacity) throws JDBCException;

    List<RoomType> getByBedsType(String bedsTypes) throws JDBCException;

    List<RoomType> getByClass(RoomType.RoomClass roomClass) throws JDBCException;  // may be with String param
}
