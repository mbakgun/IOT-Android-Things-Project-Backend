package com.mbakgun.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by burakakgun on 23-Aug-18.
 */

public class Notification implements Serializable {
    private Date sentDate = new Date();
    private String imageUrl = "";

    private String deviceId;
    private String name = "";

    private List<String> sentTo = new ArrayList<>();

    public Notification() {
    }

    public Notification(Date date, String imageUrl, Device device) {
        setSentDate(date);
        setImageUrl(imageUrl);
        setDeviceId(device.getDeviceId());
        setName(device.getName());
        setSentTo(device.getFcmTokenList());
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getSentTo() {
        return sentTo;
    }

    public void setSentTo(List<String> sentTo) {
        this.sentTo = sentTo;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "sentDate=" + sentDate +
                ", imageUrl='" + imageUrl + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", sentTo=" + sentTo +
                '}';
    }
}