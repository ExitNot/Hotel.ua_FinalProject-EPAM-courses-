package com.epam.courses.java.final_project.util;

public class SQLConstant {

    public static final String DELETE = "DELETE FROM table WHERE id = ?;";
    public static final String SELECT_BY = "SELECT * FROM table WHERE param = ?;";
    public static final String SELECT_ALL = "SELECT * FROM table;";

//    Reservations
    public static final String RESERVATION_INSERT = "INSERT INTO reservations (user_id, room_id, date_from, date_to, guests_amount) VALUES (?, ?, ?, ?, ?);";
    public static final String OCCUPIED_ROOMS = "SELECT * FROM reservations WHERE (date_from < ? AND date_to > ?);";
    public static final String RESERVATION_UPDATE = "UPDATE reservations SET user_id = ?, room_id = ?, date_from = ?, date_to = ?, guests_amount = ? WHERE id = ?;";

//    Requests
    public static final String REQUEST_INSERT = "INSERT INTO requests (user_id, room_id, date_from, date_to, adults_amount, children_amount, status, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
    public static final String REQUESTED_ROOMS = "SELECT * FROM requests WHERE (date_from < ? AND date_to > ?);";
    public static final String REQUEST_UPDATE = "UPDATE requests SET user_id = ?, room_id = ?, date_from = ?, date_to = ?, adults_amount = ?, children_amount = ?, status = ?, price = ? WHERE id = ?";

//    Users
    public static final String USER_INSERT = "INSERT INTO users (email, password, name, surname, phone_number, role) VALUES (?, ?, ?, ?, ?, ?);";
    public static final String USER_UPDATE = "UPDATE users SET email = ?, password = ?, name = ?, surname = ?, phone_number = ?, role = ? WHERE id = ?;";

//    Rooms
    public static final String ROOM_INSERT = "INSERT INTO rooms (room_number, floor, room_type_id) VALUES (?, ?, ?);";
    public static final String ROOM_UPDATE = "UPDATE rooms SET room_number = ?, floor = ?, room_type_id = ? WHERE id = ?;";

//    Room types
    public static final String ROOM_TYPE_INSERT = "INSERT INTO room_types (capacity, beds_types, class, description) VALUES (?, ?, ?, ?);";
    public static final String ROOM_TYPE_UPDATE = "UPDATE room_types SET capacity = ?, beds_types = ?, class = ?, description = ? WHERE id = ?;";

//    Room images
    public static final String IMG_INSERT = "INSERT INTO room_images (room_type_id, img_path) VALUES (?, ?);";
    public static final String IMG_UPDATE = "UPDATE room_images SET room_type_id = ?, img_path = ? WHERE id = ?;";
}