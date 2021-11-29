package com.epam.courses.java.final_project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.epam.courses.java.final_project.util.constant.Constant.LOG_TRACE;

/**
 * The {@code Room} class represent corresponding entity from database.
 * Room entity must have room class, which represent level of comfortable of this room.
 * <p>
 *     ~ {@code Standard} represent the most basic type of room offered by the hotel
 * <p>
 *     ~ {@code Deluxe} A step above standard, moderate and superior rooms,
 *     deluxe accommodations combine a desirable view with a number of luxurious amenities
 * <p>
 *     ~ {@code Suite} offers a separate sleeping and working area
 *
 * @author Kostiantyn Kolchenko
 * */
public class Room {

    private static final Logger log = LogManager.getLogger(LOG_TRACE);

    private long id;
    private int roomNumber;
    private int floor;
    private long roomTypeId;
    private RoomType roomType;

    public Room(long id, int roomNumber, int floor, long roomTypeId) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomTypeId = roomTypeId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", floor=" + floor +
                ", type=" + roomType +
                '}';
    }
}
