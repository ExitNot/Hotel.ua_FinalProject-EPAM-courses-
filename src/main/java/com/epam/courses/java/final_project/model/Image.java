package com.epam.courses.java.final_project.model;

/**
 * The {@code Image} class represent corresponding entity from database.
 * {@code Image} correspond to one or several roomTypes. Image
 *
 * @author Kostiantyn Kolchenko
 */
public class Image {

    long roomTypeId;
    String path;

    public Image(long roomTypeId, String path) {
        this.roomTypeId = roomTypeId;
        this.path = path;
    }

    public long getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(long roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
