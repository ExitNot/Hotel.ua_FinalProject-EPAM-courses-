package com.epam.courses.java.final_project.dao.entity;

/**
 * The {@code Request} class represent corresponding entity from database.
 *
 * @author Kostiantyn Kolchenko
 * */
public class Reservation {

    int id;
    int roomId;
    String from;
    String to;
    int guests_amount;

    public Reservation() {}

    public Reservation(int id, int roomId, String from, String to, int guests_amount) {
        this.id = id;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
        this.guests_amount = guests_amount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getGuests_amount() {
        return guests_amount;
    }

    public void setGuests_amount(int guests_amount) {
        this.guests_amount = guests_amount;
    }
}
