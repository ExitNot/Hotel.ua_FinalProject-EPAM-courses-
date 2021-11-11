package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.model.Room;
import com.epam.courses.java.final_project.dao.impl.jdbc.JDBCException;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Room.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface RoomDao extends AbstractDao<Room> {

    Optional<Room> getByRoomNum(int roomNum) throws JDBCException;

    List<Room> getRoomsByFloor(int floor) throws JDBCException;

    List<Room> getRoomsByCapacity(int capacity) throws JDBCException;

    List<Room> getRoomsByBedsTypes(String bedsTypes) throws JDBCException;

    List<Room> getRoomsByClass(Room.RoomClass roomClass) throws JDBCException;  // may be with String param
}
