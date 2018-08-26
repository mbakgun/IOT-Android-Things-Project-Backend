package com.mbakgun.spring.request;


import com.mbakgun.spring.util.constants.RequestConstant;
import com.mbakgun.spring.util.exception.MbaException;
import com.mbakgun.spring.util.exception.RequiredFieldException;

import org.json.JSONException;
import org.json.JSONObject;

public class PostConfigureRequest {
    private boolean active;
    private String name;

    public PostConfigureRequest() {
    }

    public PostConfigureRequest(boolean active, String name) {
        this.active = active;
        this.name = name;
    }

    public static PostConfigureRequest convertRequestBodyToPostConfigureRequestModel(String requestBody) throws MbaException, JSONException {
        PostConfigureRequest par = new PostConfigureRequest();
        JSONObject obj = new JSONObject(requestBody);
        if (obj.has(RequestConstant.ACTIVATED)) {
            par.setActive(obj.getBoolean(RequestConstant.ACTIVATED));
        } else {
            throw new RequiredFieldException("error.missing.activated");
        }
        if (obj.has(RequestConstant.DEVICE_NAME)) {
            par.setName(obj.getString(RequestConstant.DEVICE_NAME));
        }
        return par;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PostConfigureRequest{" +
                "active=" + active +
                ", name='" + name + '\'' +
                '}';
    }
}