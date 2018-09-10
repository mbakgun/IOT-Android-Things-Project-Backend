package com.mbakgun.spring.service.RedisService;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mbakgun.spring.service.AbstractServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_TOKEN;

@Service
public class RedisServiceImpl extends AbstractServiceImpl implements RedisService {
    @Autowired
    private StringRedisTemplate redis;

    @Override
    public boolean isExist(String path) {
        return redis.hasKey(path);
    }

    @Override
    public boolean isExist(String keyPrefix, String UUID) {
        return redis.hasKey(keyPrefix + ":" + UUID);
    }

    @Override
    public String getDeviceId(String generatedToken) {
        return redis.opsForValue().get(REDIS_PREFIX_TOKEN + ":" + generatedToken);
    }

    @Override
    public void saveObject(Object savingObject, String keyPrefix, String key) {
        Gson gson = new Gson();
        redis.opsForValue().set(keyPrefix + ":" + key, gson.toJson(savingObject));
    }

    @Override
    public void saveValue(String keyPrefix, String key, String value) {
        redis.opsForValue().set(keyPrefix + ":" + key, value);
    }

    @Override
    public <T> T getObject(String keyPrefix, String key, TypeToken objectClass) {
        String object = redis.opsForValue().get(keyPrefix + ":" + key);
        if (!StringUtils.isEmpty(object)) {
            Gson gson = new Gson();
            return gson.fromJson(object, objectClass.getType());
        }
        return null;
    }

    @Override
    public Map<String, Object> getMapForKey(String keyPrefix, String key, TypeToken objectClass) {
        Map<String, Object> map = new HashMap();
        Set<String> keys = redis.keys(keyPrefix + ":" + key + "*");
        for (String catchKey : keys) {
            map.put(catchKey, getObject(keyPrefix, catchKey.replaceAll((keyPrefix + ':'), ""), objectClass));
        }
        return map;
    }

    @Override
    public boolean deleteObject(String keyPrefix, String key) {
        redis.delete(keyPrefix + ':' + key);
        return !isExist(keyPrefix, key);
    }
}