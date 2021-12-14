package com.epam.courses.java.final_project.util.constant;

public class CommandConstant {

    public static final String NOT_FOUND = "Not found";

//    GET commands
    public static final String INDEX = "index";
    public static final String AVAILABLE_ROOMS = "availableRooms";
    public static final String SIGN_IN = "signIn";
    public static final String PROFILE = "profile";
    public static final String LOGOUT = "logout";
    public static final String ROOM_TYPE = "roomType";
    public static final String MY_REQUESTS = "myRequests";
    public static final String MY_RESERVATIONS = "myReservations";
    public static final String REQUEST_LIST = "requestsList";
    public static final String REQUEST = "request";
    public static final String ACCEPT = "acceptRequest";
    public static final String PAYMENT = "payment";
    public static final String REQUEST_RESPONSE = "requestResponse";

//    POST commands
    public static final String SIGN_UP = "signUp";
    public static final String DELETE_USER = "deleteUser";
    public static final String BOOK_SPECIFIC_ROOM = "bookSpecificRoom";
    public static final String CREATE_REQUEST = "createRequest";
    public static final String CANCEL_REQUEST = "cancelRequest";
    public static final String CANCEL_RESERVATION = "cancelReservation";
    public static final String PWD_UPDATE = "pwdUpdate";
    public static final String USER_UPDATE = "userUpdate";

//    Parameters
    public static final String PARAM_LANG = "language";
    public static final String PARAM_ID = "id";
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_ROOM_ID = "roomId";
    public static final String PARAM_ASSIGNED_ROOM_ID = "assignedRoomId";
    public static final String PARAM_REQUEST_ID = "requestId";
    public static final String PARAM_REQUEST_RC = "requestRCVal";
    public static final String PARAM_RESERVATION_ID = "reservationId";
    public static final String PARAM_ROOM_TYPE_ID = "typeId";
    public static final String PARAM_PWD = "pwd";  // User
    public static final String PARAM_NEW_PWD = "newPwd";
    public static final String PARAM_PHONE_NUM = "phoneNumber";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_ROLE = "role";
    public static final String PARAM_FLOOR = "floor";
    public static final String PARAM_ROOM_TYPE = "type";
    public static final String PARAM_ROOM_CLASS = "roomClass";
    public static final String PARAM_CAPACITY = "capacity";
    public static final String PARAM_ADULTS_AMOUNT = "amountOfAdultsIn_room";
    public static final String PARAM_CHILDREN_AMOUNT = "amountOfChildrenIn_room";
    public static final String PARAM_SEARCH_BTN = "searchBtn";
    public static final String PARAM_WAITING_FOR_RESPONSE = "waitingForResponse";

    public static final String PARAM_FROM = "dateFrom";  // Reservation
    public static final String PARAM_TO = "dateTo";

//    Attributes
    public static final String ATTRIBUTE_LANG = "language";
    public static final String ATTRIBUTE_ID = "id";
    public static final String ATTRIBUTE_EMAIL = "email";
    public static final String ATTRIBUTE_ROLE = "role";
    public static final String ATTRIBUTE_USER = "user";
    public static final String ATTRIBUTE_ROOM_TYPE = "roomType";
    public static final String ATTRIBUTE_ROOMS_LIST = "roomsList";
    public static final String ATTRIBUTE_ROOM_TYPES_LIST = "roomTypesList";
    public static final String ATTRIBUTE_REQUEST_LIST = "requestsList";
    public static final String ATTRIBUTE_REQUEST = "request";
    public static final String ATTRIBUTE_REQUEST_BUNDLE = "requestBundle";
    public static final String ATTRIBUTE_RESERVATIONS_LIST = "userReservationsList";
    public static final String ATTRIBUTE_ROOM_CLASS = "roomClass";
    public static final String ATTRIBUTE_CAPACITY = "capacity";
    public static final String ATTRIBUTE_FLOOR = "floor";
    public static final String ATTRIBUTE_FROM = "dateFrom";
    public static final String ATTRIBUTE_TO = "dateTo";
    public static final String ATTRIBUTE_INDEX_NOTIFICATION = "indexNotification";
    public static final String ATTRIBUTE_EX = "exception";  // exception
    public static final String ATTRIBUTE_LOGIN_EX = "loginEx";  // exception
    public static final String ATTRIBUTE_USER_UPDATE_EX = "userUpdateEx";  // exception
    public static final String ATTRIBUTE_SIGN_UP_EX = "signUpEx";  // exception
    public static final String ATTRIBUTE_ROOMS_LIST_EX = "roomsListEx";  // exception
    public static final String ATTRIBUTE_REQUEST_RESPONSE_EX = "requestResponseEx";  // exception

//    Pages JSP
    public static final String SIGN_UP_JSP = "signUp.jsp";
    public static final String ROOM_TYPE_JSP = "roomType.jsp";
    public static final String PROFILE_JSP = "profile.jsp";
    public static final String EDIT_PROFILE_JSP = "editProfile.jsp";
    public static final String INDEX_JSP = "index.jsp";
    public static final String REQUEST_JSP = "createRequest.jsp";
    public static final String AVAILABLE_ROOMS_JSP = "availableRooms.jsp";
    public static final String REQUEST_RESPONSE_JSP = "requestResponse.jsp";
    public static final String MY_RESERVATIONS_JSP = "myReservations.jsp";
    public static final String MY_REQUESTS_JSP = "myRequests.jsp";
    public static final String REQUEST_LIST_JSP = "requestsList.jsp";
    public static final String ERROR_JSP = "error.jsp";

//    Pages for Controller
    public static final String PROFILE_ACT = "profile.act";
    public static final String INDEX_ACT = "index.act";
    public static final String MY_RESERVATIONS_ACT = "myReservations.act";
    public static final String MY_REQUESTS_ACT = "myRequests.act";
    public static final String REQUEST_LIST_ACT = "requestsList.act";
}
