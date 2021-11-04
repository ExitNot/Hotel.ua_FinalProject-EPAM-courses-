package com.epam.courses.java.final_project.dao.entity;

import java.util.List;

/**
 * The {@code Room} class represent corresponding entity from database.
 *
 * @author Kostiantyn Kolchenko
 * */
public class Room {

    int id;
    int roomNumber;
    int capacity;
    String bedType;  // S - single, D - double. String in form ("1D, 2S") etc...
    RoomClass roomClass;
    List<String> imgPaths;

    public Room() {}

    public Room(int id, int roomNumber, int capacity, String bedType, RoomClass roomClass) {
        this.id = id;
        this.roomNumber = roomNumber;
        this.capacity = capacity;
        this.bedType = bedType;
        this.roomClass = roomClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
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

    enum RoomClass{
        Standard(1), Deluxe(2), Suite(3);

        int value;

        RoomClass(int num) {
            value = num;
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
