package com.mbakgun.spring.request;


import com.mbakgun.spring.util.constants.RequestConstant;
import com.mbakgun.spring.util.exception.MbaException;
import com.mbakgun.spring.util.exception.RequiredFieldException;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class DeleteNotificationRequest {
    private Date sentDate;

    public DeleteNotificationRequest() {
    }

    public static DeleteNotificationRequest convertRequestBodyToDeleteNotificationRequestModel(String requestBody) throws MbaException, JSONException {
        DeleteNotificationRequest deleteNotificationRequest = new DeleteNotificationRequest();
        JSONObject obj = new JSONObject(requestBody);
        if (obj.has(RequestConstant.SENT_DATE)) {
            deleteNotificationRequest.setSentDate(new Date(obj.getLong(RequestConstant.SENT_DATE)));
        } else {
            throw new RequiredFieldException("error.missing.sentDate");
        }
        return deleteNotificationRequest;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    @Override
    public String toString() {
        return "DeleteNotificationRequest{" +
                "sentDate=" + sentDate +
                '}';
    }
}