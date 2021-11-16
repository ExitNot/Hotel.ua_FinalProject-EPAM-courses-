package com.epam.courses.java.final_project.util;

public class SQLConstant {

    public static final String DELETE = "DELETE FROM table WHERE id = ?;";
    public static final String SELECT_BY = "SELECT * FROM table WHERE param = ?;";
    public static final String SELECT_ALL = "SELECT * FROM table;";

//    Reservation
    public static final String RESERVATION_INSERT = "INSERT INTO reservations (user_id, room_id, date_from, date_to, guests_amount) VALUES (?, ?, ?, ?, ?);";
    public static final String OCCUPIED_ROOMS = "SELECT * FROM reservations WHERE (date_from < ? AND date_to > ?);";
    public static final String RESERVATION_UPDATE = "UPDATE reservations SET user_id = ?, room_id = ?, date_from = ?, date_to = ?, guests_amount = ? WHERE id = ?;";

//    Request
    public static final String REQUEST_INSERT = "INSERT INTO request (user_id, room_id, date_from, date_to, adults_amount, children_amount, status, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String REQUESTED_ROOMS = "SELECT * FROM request WHERE (date_from < ? AND date_to > ?);";
    public static final String REQUEST_UPDATE = "UPDATE request SET user_id = ?, room_id = ?, date_from = ?, date_to = ?, adults_amount = ?, children_amount = ?, status = ?, price = ? WHERE id = ?";

//    User
    public static final String USER_INSERT = "INSERT INTO users (login, password, name, surname, phone_number, email, role) VALUES (?, ?, ?, ?, ?, ?, ?);";
    public static final String USER_UPDATE = "UPDATE users SET login = ?, password = ?, name = ?, surname = ?, phone_number = ?, email = ?, role = ? WHERE id = ?;";

//    Room
    public static final String ROOM_INSERT = "INSERT INTO rooms (room_number, floor, capacity, beds_types, class) VALUES (?, ?, ?, ?, ?);";
    public static final String ROOM_UPDATE = "UPDATE rooms SET room_number = ?, floor = ?, capacity = ?, beds_types = ?, class = ? WHERE id = ?;";
}