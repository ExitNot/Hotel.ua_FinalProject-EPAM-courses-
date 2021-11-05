package com.epam.courses.java.final_project.dao;

import com.epam.courses.java.final_project.dao.entity.Room;

import java.util.List;
import java.util.Optional;

/**
 * DAO interface for Room.
 *
 * @author Kostiantyn Kolchenko
 * */
public interface RoomDao extends AbstractDao<Room> {

    Optional<Room> getByRoomNum(int roomNum);

    List<Room> getRoomsByFloor(int floor);

    List<Room> getRoomsByCapacity(int capacity);

    List<Room> getRoomsByBedsTypes(String bedsTypes);

    List<Room> getRoomsByClass(Room.RoomClass roomClass);  // may be with String param
}
