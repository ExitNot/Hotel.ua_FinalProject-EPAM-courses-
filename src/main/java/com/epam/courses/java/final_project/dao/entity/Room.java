package com.epam.courses.java.final_project.dao.entity;

import java.util.List;

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

    long id;
    int roomNumber;
    int floor;
    int capacity;
    String bedType;  // S - single, D - double. String in form ("1D, 2S") etc...
    RoomClass roomClass;
    List<String> imgPaths;

    public Room() {}

    public Room(long id, int roomNumber, int floor, int capacity, String bedType, RoomClass roomClass) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.floor = floor;
        this.capacity = capacity;
        this.bedType = bedType;
        this.roomClass = roomClass;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getBedType() {
        return bedType;
    }

    public void setBedType(String bedType) {
        this.bedType = bedType;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(int value) {
        this.roomClass = RoomClass.getRoomClass(value);
    }


    public List<String> getImgPaths() {
        return imgPaths;
    }

    public void addImgPath(String path) {
        imgPaths.add(path);
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

}
