package com.epam.courses.java.final_project.model;

import java.util.Objects;

/**
 * The {@code Room} class represent corresponding entity from database.
 * {@code Room} have to belong to one of {@code RoomType}'s.
 *
 * @author Kostiantyn Kolchenko
 */
public class Room {

    private long id;
    private int roomNumber;
    private int floor;
    private long roomTypeId;
    private RoomType roomType;

    public Room(){}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return id == room.id && roomNumber == room.roomNumber && floor == room.floor && roomTypeId == room.roomTypeId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomNumber, floor, roomTypeId, roomType);
    }
}
