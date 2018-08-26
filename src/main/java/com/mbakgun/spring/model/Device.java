package com.mbakgun.spring.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by burakakgun on 23-Aug-18.
 */

public class Device implements Serializable {
    private String deviceId;
    private String name = "";
    private boolean active = true;
    private Date createDate = new Date();
    private String generatedToken;
    private List<String> fcmTokenList = new ArrayList<>();

    public Device() {
    }

    public Device(String deviceId, String name) {
        this.deviceId = deviceId;
        this.name = name;
    }

    public Device(String deviceId, String name, boolean active, Date createDate, String generatedToken, List<String> fcmTokenList) {
        this.deviceId = deviceId;
        this.name = name;
        this.active = active;
        this.createDate = createDate;
        this.generatedToken = generatedToken;
        this.fcmTokenList = fcmTokenList;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }

    public List<String> getFcmTokenList() {
        return fcmTokenList;
    }

    public void setFcmTokenList(List<String> fcmTokenList) {
        this.fcmTokenList = fcmTokenList;
    }

    public void addFcmTokenList(String fcmToken) {
        fcmTokenList.add(fcmToken);
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId='" + deviceId + '\'' +
                ", name='" + name + '\'' +
                ", active=" + active +
                ", createDate=" + createDate +
                ", generatedToken='" + generatedToken + '\'' +
                ", fcmTokenList=" + fcmTokenList +
                '}';
    }
}