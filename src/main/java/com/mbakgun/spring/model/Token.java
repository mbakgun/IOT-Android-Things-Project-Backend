package com.mbakgun.spring.model;

import java.io.Serializable;
import java.util.UUID;

/**
 * Created by burakakgun on 23-Aug-18.
 */

public class Token implements Serializable {
    private String deviceId = UUID.randomUUID().toString();
    private String generatedToken = UUID.randomUUID().toString();

    public Token() {
    }

    public Token(String deviceId, String generatedToken) {
        this.deviceId = deviceId;
        this.generatedToken = generatedToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }

    @Override
    public String toString() {
        return "Token{" +
                "deviceId='" + deviceId + '\'' +
                ", generatedToken='" + generatedToken + '\'' +
                '}';
    }
}