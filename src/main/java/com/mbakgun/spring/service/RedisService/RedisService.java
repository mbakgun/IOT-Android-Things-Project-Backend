package com.mbakgun.spring.service.RedisService;

import com.google.gson.reflect.TypeToken;

import java.util.Map;

public interface RedisService {
    boolean isExist(String keyPrefix, String UUID);

    String getDeviceId(String generatedToken);

    void saveObject(Object objectSave, String keyPrefix, String key);

    void saveValue(String keyPrefix, String key, String value);

    <T> T getObject(String keyPrefix, String key, TypeToken objectClass);

    Map<String, Object> getMapForKey(String keyPrefix, String key, TypeToken objectClass);
}