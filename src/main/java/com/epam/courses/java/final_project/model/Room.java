package com.epam.courses.java.final_project.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.epam.courses.java.final_project.util.Constant.LOG_TRACE;

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
    private long roomType;

    public Room(long id, int roomNumber, int floor, long roomType) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.roomType = roomType;
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

    public long getRoomType() {
        return roomType;
    }

    public void setRoomType(long roomType) {
        this.roomType = roomType;
    }

    public enum RoomClass{
        Standard(1), Deluxe(2), Suite(3);

        int value;

        RoomClass(int num) {
            value = num;
        }

        public int getValue() {
            return value;
        }

        public static RoomClass getRoomClass(int num){
            if (num == 3)
                return Suite;
            else if (num == 2)
                return Deluxe;
            return Standard;
        }
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomNumber=" + roomNumber +
                ", floor=" + floor +
                '}';
    }
}
