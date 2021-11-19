package com.epam.courses.java.final_project.util;

public class Constant {

//    SQL
    public static SQLConstant SQL;

//    DB columns and tables (parameters)
    public static final String TABLE_USER = "users";
    public static final String TABLE_ROOM = "rooms";
    public static final String TABLE_ROOM_TYPE = "room_types";
    public static final String TABLE_IMG = "room_images";
    public static final String TABLE_RESERVATION = "reservations";
    public static final String TABLE_REQUEST = "requests";

    public static final String PARAM_ID = "id";  // Identifiers
    public static final String PARAM_USER_ID = "user_id";
    public static final String PARAM_ROOM_ID = "room_id";
    public static final String PARAM_ROOM_TYPE_ID = "room_type_id";
    public static final String PARAM_REQUEST_ID = "request_id";

    public static final String PARAM_LOGIN = "login";  // User
    public static final String PARAM_PWD = "password";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_PHONE_NUMBER = "phone_number";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_ROLE = "role";

    public static final String PARAM_ROOM_NUM = "room_number";  // Room
    public static final String PARAM_FLOOR = "floor";
    public static final String PARAM_CAPACITY = "capacity";
    public static final String PARAM_BED_TYPE = "beds_types";
    public static final String PARAM_CLASS = "class";

    public static final String PARAM_IMG_PATH = "img_path";
    public static final String PARAM_DESCRIPTION = "description";

    public static final String PARAM_DATE_FROM = "date_from";  // Reservation
    public static final String PARAM_DATE_TO = "date_to";
    public static final String PARAM_DATE_OF_CUSTOMER_ACCEPTANCE = "date_of_customer_acceptance";
    public static final String PARAM_GUESTS_AMOUNT = "guests_amount";

    public static final String PARAM_STATUS = "status";  // Request
    public static final String PARAM_PRICE = "price";
    public static final String PARAM_ADULTS_AMOUNT = "adults_amount";
    public static final String PARAM_CHILDREN_AMOUNT = "children_amount";

//    Loggers
    public static final String LOG_TRACE = "trace";
    public static final String LOG_ERROR = "error";

//    Commands
    public static CommandConstant COMMAND;
}
