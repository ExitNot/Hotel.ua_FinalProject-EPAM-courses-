package com.epam.courses.java.final_project.model;

public class Image {

    long room_id;
    String path;

    public Image(long room_id, String path) {
        this.room_id = room_id;
        this.path = path;
    }

    public long getRoom_id() {
        return room_id;
    }

    public void setRoom_id(long room_id) {
        this.room_id = room_id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
