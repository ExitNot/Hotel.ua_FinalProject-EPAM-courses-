package com.epam.courses.java.final_project.service;

import com.epam.courses.java.final_project.dao.DAOFactory;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;
import com.epam.courses.java.final_project.model.Image;
import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.model.RoomType;

import java.util.List;
import java.util.Optional;

/**
 * Service class that provide access to {@code RoomTypeDao} and {@code RoomTypeDao}.
 *
 * @author Kostiantyn Kolchenko
 * @see DAOFactory
 * @see RoomType
 */
public class RoomTypeService {

    public static Optional<RoomType> getById(long id) throws JDBCException {
        return DAOFactory.getInstance().getRoomTypeDao().getById(id);
    }

    public static List<RoomType> getAll() throws JDBCException {
        return DAOFactory.getInstance().getRoomTypeDao().getAll();
    }

    public static List<RoomType> getByCapacity(int capacity) throws JDBCException {
        return DAOFactory.getInstance().getRoomTypeDao().getByCapacity(capacity);
    }

    public static List<Image> getImg(long id) throws JDBCException {
        return DAOFactory.getInstance().getImageDao().getByRoomType(id);
    }
}
