package com.mbakgun.spring.request;


import com.mbakgun.spring.util.constants.RequestConstant;
import com.mbakgun.spring.util.entity.Platform;
import com.mbakgun.spring.util.exception.MbaException;
import com.mbakgun.spring.util.exception.RequiredFieldException;
import com.mbakgun.spring.util.network.RequestContext;

import org.json.JSONException;
import org.json.JSONObject;

public class PostAuthRequest {
    private String deviceId;
    private String fcmToken;

    public PostAuthRequest() {
    }

    public PostAuthRequest(String deviceId, String fcmToken) {
        this.deviceId = deviceId;
        this.fcmToken = fcmToken;
    }

    public static PostAuthRequest convertRequestBodyToPostAuthRequestModel(String requestBody) throws MbaException, JSONException {
        PostAuthRequest par = new PostAuthRequest();
        JSONObject obj = new JSONObject(requestBody);
        if (obj.has(RequestConstant.DEVICE_ID)) {
            par.setDeviceId(obj.getString(RequestConstant.DEVICE_ID));
        } else {
            throw new RequiredFieldException("error.missing.deviceId");
        }
        RequestContext context = RequestContext.getContext();
        if (context.getPlatform() == Platform.ANDROID) {
            if (obj.has(RequestConstant.FCM_TOKEN)) {
                par.setFcmToken(obj.getString(RequestConstant.FCM_TOKEN));
            } else {
                throw new RequiredFieldException("error.missing.fcmToken");
            }
        }
        return par;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getFcmToken() {
        return fcmToken;
    }

    public void setFcmToken(String fcmToken) {
        this.fcmToken = fcmToken;
    }

    @Override
    public String toString() {
        return "PostAuthRequest{" +
                "deviceId='" + deviceId + '\'' +
                ", fcmToken='" + fcmToken + '\'' +
                '}';
    }
}