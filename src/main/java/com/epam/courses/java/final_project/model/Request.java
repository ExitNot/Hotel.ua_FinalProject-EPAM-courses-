package com.epam.courses.java.final_project.model;

import java.sql.Date;
import java.util.Locale;

/**
 * The {@code Request} class represent corresponding entity from database.
 * Users create requests for reservations and this request pass through 3 steps. Status field represent this steps.
 * <p>
 * ~ {@code ManagerResponse} - request is waiting for {@code manager} response
 * @author Kostiantyn Kolchenko
 */
public class Request extends Reservation {

    Status status;
    double price;
    int adultsAmount;
    int childrenAmount;
    Date managerAcceptance;
    RoomType.RoomClass rc;

    public Request(long userId, Date from, Date to, int adultsAmount, int childrenAmount, Status status, double price) {
        super(userId, 0, from, to, adultsAmount + childrenAmount);
        this.adultsAmount = adultsAmount;
        this.childrenAmount = childrenAmount;
        this.status = status;
        this.price = price;
    }

    public Request(long userId, long roomId, Date from, Date to, int adultsAmount, int childrenAmount, Status status, double price) {
        super(userId, roomId, from, to, adultsAmount + childrenAmount);
        this.adultsAmount = adultsAmount;
        this.childrenAmount = childrenAmount;
        this.status = status;
        this.price = price;
    }

    public Request(long id, long userId, long roomId, Date from, Date to, Date managerAcceptance, int adultsAmount,
                   int childrenAmount, RoomType.RoomClass rc, Status status, double price) {
        super(id, userId, roomId, from, to, adultsAmount + childrenAmount);
        this.managerAcceptance = managerAcceptance;
        this.adultsAmount = adultsAmount;
        this.childrenAmount = childrenAmount;
        this.rc = rc;
        this.status = status;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAdultsAmount() {
        return adultsAmount;
    }

    public void setAdultsAmount(int adultsAmount) {
        this.adultsAmount = adultsAmount;
    }

    public int getChildrenAmount() {
        return childrenAmount;
    }

    public void setChildrenAmount(int childrenAmount) {
        this.childrenAmount = childrenAmount;
    }

    public Status getStatus() {
        return status;
    }

    public String getStatusName() {
        switch (status.value){
            case 1:
                return "Waiting for manager response";
            case 2:
                return "Waiting for accept";
            default:
                return status.name();
        }
    }

    public void setStatus(int value) {
        this.status = Status.getStatus(value);
    }

    public Date getManagerAcceptance() {
        return managerAcceptance;
    }

    public void setManagerAcceptance(Date managerAcceptance) {
        this.managerAcceptance = managerAcceptance;
    }

    public RoomType.RoomClass getRc() {
        return rc;
    }

    public void setRc(RoomType.RoomClass rc) {
        this.rc = rc;
    }

    public enum Status {
        ManagerResponse(1), CustomerAccept(2), Payment(3), Canceled(4);

        int value;

        Status(int num) {
            value = num;
        }

        public int getValue() {
            return value;
        }

        public static Status getStatus(int num) {
            if (num == 4){
                return Canceled;
            } else if (num == 3)
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
                ", adultsAmount=" + adultsAmount +
                ", childrenAmount=" + childrenAmount +
                ", email=" + getUserEmail() +
                ", roomNumber=" + getRoomNumber() +
                '}';
    }
}
