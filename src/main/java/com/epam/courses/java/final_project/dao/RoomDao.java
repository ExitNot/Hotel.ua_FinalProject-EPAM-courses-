package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for {@code Room}.
 *
 * @see Room
 * @author Kostiantyn Kolchenko
 **/
public interface RoomDao extends AbstractDao<Room> {

    Optional<Room> getByRoomNum(int roomNum) throws JDBCException;

    List<Room> getRoomsByFloor(int floor) throws JDBCException;

}
