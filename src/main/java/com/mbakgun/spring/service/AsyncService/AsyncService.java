package com.mbakgun.spring.service.AsyncService;


import com.mbakgun.spring.model.Notification;

public interface AsyncService {
    void sendNotificationToServerAsync(byte[] byteImageArray, String path, String fileName, String extension, Notification notification);
}