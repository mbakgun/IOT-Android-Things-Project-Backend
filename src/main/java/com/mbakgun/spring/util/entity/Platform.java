package com.mbakgun.spring.util.entity;

/**
 * Created by burakakgun on 19-Feb-18.
 */

public enum Platform {
    ANDROID, IOS, NULL, THINGS;

    public static Platform getEnum(String platform) {
        if (ANDROID.name().equalsIgnoreCase(platform)) {
            return ANDROID;
        }
        if (THINGS.name().equalsIgnoreCase(platform)) {
            return THINGS;
        } else if (IOS.name().equalsIgnoreCase(platform)) {
            return IOS;
        }
        throw new IllegalArgumentException("No Enum specified for Platform :'" + platform + "'");
    }
}