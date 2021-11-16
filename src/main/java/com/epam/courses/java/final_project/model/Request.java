package com.epam.courses.java.final_project.model;

import java.sql.Date;

/**
 * The {@code Request} class represent corresponding entity from database.
 *
 * @author Kostiantyn Kolchenko
 * */
public class Request extends Reservation{

    Status status;
    double price;

    public Request(long id, long userId, long roomId, Date from, Date to, int guests_amount, Status status, double price) {
        super(id, userId, roomId, from, to, guests_amount);
        this.status = status;
        this.price = price;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(int value) {
        this.status = Status.getStatus(value);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public enum Status{
        ManagerResponse(1), CustomerAccept(2), Payment(3);  // waiting for

        int value;

        Status(int num) {
            value = num;
        }

        public static Status getStatus(int num){
            if (num == 3)
                return Payment;
            else if (num == 2)
                return CustomerAccept;
            return ManagerResponse;
        }
    }

    @Override
    public String toString() {
        return "Request{" +
                "status=" + status +
                ", price=" + price +
                ", id=" + id +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", from=" + from +
                ", to=" + to +
                ", guests_amount=" + guests_amount +
                '}';
    }
}
