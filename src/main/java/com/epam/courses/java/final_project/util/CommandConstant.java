package com.epam.courses.java.final_project.util;

public class CommandConstant {

    public static final String NOT_FOUND = "Not found";

//    GET commands
    public static final String INDEX = "index";
    public static final String AVAILABLE_ROOMS = "availableRooms";
    public static final String SIGN_IN = "signIn";
    public static final String PROFILE = "profile";
    //    POST commands
    public static final String SIGN_UP = "signUp";
    public static final String DELETE_USER = "deleteUser";
    public static final String BOOK_SPECIFIC_ROOM = "bookSpecificRoom";

//    Parameters
    public static final String PARAM_USER_ID = "userId";
    public static final String PARAM_ROOM_ID = "roomId";
    public static final String PARAM_LOGIN = "login";  // User
    public static final String PARAM_PWD = "pwd";
    public static final String PARAM_PHONE_NUM = "phoneNumber";
    public static final String PARAM_NAME = "name";
    public static final String PARAM_SURNAME = "surname";
    public static final String PARAM_PHONE_NUMBER = "phone_number";
    public static final String PARAM_EMAIL = "email";
    public static final String PARAM_ROLE = "role";

    public static final String PARAM_FROM = "dateFrom";
    public static final String PARAM_TO = "dateTo";

//    Attributes
    public static final String ATTRIBUTE_ID = "id";
    public static final String ATTRIBUTE_LOGIN = "login";
    public static final String ATTRIBUTE_ROLE = "role";
    public static final String ATTRIBUTE_ROOMS_LIST = "roomsList";
    public static final String ATTRIBUTE_USER_REQUEST_LIST = "userRequestList";
    public static final String ATTRIBUTE_FROM = "dateFrom";
    public static final String ATTRIBUTE_TO = "dateTo";
    public static final String ATTRIBUTE_LOGIN_ERROR = "loginError";  // exception
    public static final String ATTRIBUTE_SIGN_UP_ERROR = "signUpError";  // exception
    public static final String ATTRIBUTE_ROOMS_LIST_ERROR = "roomsListError";  // exception

//    Pages JSP
    public static final String SIGN_IN_JSP = "signIn.jsp";
    public static final String SIGN_UP_JSP = "signUp.jsp";
    public static final String PROFILE_JSP = "profile.jsp";
    public static final String INDEX_JSP = "index.jsp";
    public static final String AVAILABLE_ROOMS_JSP = "availableRoomsList.jsp";

//    Pages for Controller
    public static final String PROFILE_ACT = "profile.act";
//public static final String LOGIN_P = "login.p";
}
