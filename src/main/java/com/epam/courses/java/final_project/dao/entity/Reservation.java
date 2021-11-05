package com.epam.courses.java.final_project.dao.entity;

import java.sql.Date;

/**
 * The {@code Request} class represent corresponding entity from database.
 *
 * @author Kostiantyn Kolchenko
 * */
public class Reservation {

    long id;
    int roomId;
    Date from;
    Date to;
    int guests_amount;

    public Reservation() {}

    public Reservation(long id, int roomId, Date from, Date to, int guests_amount) {
        this.id = id;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
        this.guests_amount = guests_amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public int getGuests_amount() {
        return guests_amount;
    }

    public void setGuests_amount(int guests_amount) {
        this.guests_amount = guests_amount;
    }
}
