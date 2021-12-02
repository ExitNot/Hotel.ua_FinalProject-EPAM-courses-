package com.epam.courses.java.final_project.model;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return roomTypeId == image.roomTypeId && path.equals(image.path);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomTypeId, path);
    }
}
