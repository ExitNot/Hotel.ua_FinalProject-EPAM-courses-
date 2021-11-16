package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Room;

import java.util.List;
import java.util.Optional;

public class RoomService {

    public static List<Room> getAll() throws JDBCException {
        return DAOFactory.getInstance().getRoomDao().getAll();
    }

    public static Optional<Room> getById(Long id) throws JDBCException {
        return DAOFactory.getInstance().getRoomDao().getById(id);
    }
}
