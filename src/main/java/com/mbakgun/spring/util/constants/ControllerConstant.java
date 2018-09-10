package com.mbakgun.spring.util.constants;


public abstract class ControllerConstant {
    public static final String CONTROLLER_HEADERS = "Accept=*/*";
    public static final String CONTROLLER_JSON_PRODUCES = "application/json;charset=UTF-8";
    public static final String TOKEN_CONTROLLER_PATTERN = "/api";
    public static final String AUTH = "/authorize.json";
    public static final String REGISTRATION = "/registerThings.json";
    public static final String SEND_NOTIFICATION = TOKEN_CONTROLLER_PATTERN + "/sendNotification.json";
    public static final String GET_NOTIFICATION_LIST = TOKEN_CONTROLLER_PATTERN + "/getNotificationList.json";
    public static final String DELETE_NOTIFICATION = TOKEN_CONTROLLER_PATTERN + "/deleteNotification.json";
    public static final String SET_CONFIGURATION = TOKEN_CONTROLLER_PATTERN + "/configure.json";
}