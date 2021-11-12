package com.epam.courses.java.final_project.util;

public class SQLConstant {

    public final static String DELETE = "DELETE FROM ^ WHERE id = ?;";
    public final static String SELECT_BY = "SELECT * FROM ^ WHERE ? = ?;";
    public final static String SELECT_ALL = "SELECT * FROM ^;";

//    Reservation
    public final static String RESERVATION_INSERT = "INSERT INTO reservations (user_id, room_id, date_from, date_to, guests_amount) VALUES (?, ?, ?, ?, ?);";
    public final static String OCCUPIED_ROOMS = "SELECT * FROM reservations WHERE (date_from >= ? AND date_from <= ?) OR (date_to <= ? AND date_to >= ?) OR (date_from < ? AND date_to > ?);";
    public final static String RESERVATION_UPDATE = "UPDATE reservations SET user_id = ?, room_id = ?, date_from = ?, date_to = ?, guests_amount = ? WHERE id = ?";

//    User
    public final static String USER_INSERT = "INSERT INTO users (login, password, phone_number, email, role) VALUES (?, ?, ?, ?, ?);";
    public final static String USER_UPDATE = "UPDATE users SET login = ?, password = ?, phone_number = ?, email = ?, role = ? WHERE id = ?";

//    Room
    public final static String ROOM_INSERT = "INSERT INTO rooms (room_number, floor, capacity, beds_types, class) VALUES (?, ?, ?, ?, ?);";
    public final static String ROOM_UPDATE = "UPDATE rooms SET room_number = ?, floor = ?, capacity = ?, beds_types = ?, class = ? WHERE id = ?";
}
