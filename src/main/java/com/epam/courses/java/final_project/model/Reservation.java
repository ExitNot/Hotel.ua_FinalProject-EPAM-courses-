package com.epam.courses.java.final_project.model;

import java.sql.Date;

/**
 * The {@code Request} class represent corresponding entity from database.
 *
 * @author Kostiantyn Kolchenko
 * */
public class Reservation {

    long id;
    long userId;
    long roomId;
    Date from;
    Date to;
    int guestsAmount;
    int roomNumber;

    public Reservation(long userId, long roomId, Date from, Date to) {
        this.userId = userId;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
    }

    public Reservation(long id, long userId, long roomId, Date from, Date to, int guests_amount) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.from = from;
        this.to = to;
        this.guestsAmount = guests_amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getRoomId() {
        return roomId;
    }

    public void setRoomId(long roomId) {
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

    public int getGuestsAmount() {
        return guestsAmount;
    }

    public void setGuestsAmount(int guestsAmount) {
        this.guestsAmount = guestsAmount;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", from=" + from +
                ", to=" + to +
                ", guests_amount=" + guestsAmount +
                '}';
    }
}
