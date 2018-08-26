package com.mbakgun.spring.util.entity;

import java.io.Serializable;

public class Status implements Serializable {
    private int code;
    private String message;

    public Status(int code, String message) {
        setCode(code);
        setMessage(message);
    }

    @Override
    public String toString() {
        return "Status{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}