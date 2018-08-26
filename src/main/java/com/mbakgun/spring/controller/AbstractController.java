package com.mbakgun.spring.controller;

import com.mbakgun.spring.service.RedisService.RedisService;
import com.mbakgun.spring.util.constants.ResponseConstant;
import com.mbakgun.spring.util.entity.Status;
import com.mbakgun.spring.util.network.RequestContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractController {
    @Autowired
    protected RedisService redisService;

    private static final Logger monitorLogger = LoggerFactory.getLogger("monitorLogger");
    protected static final org.slf4j.Logger logger = LoggerFactory.getLogger("controllerLogger");

    protected ResponseEntity<Map<String, Object>> prepareSuccessResponse(int code, String msgKey, Map<String, Object> responseBody) {
        Map<String, Object> dataBody = new HashMap();
        dataBody.put(ResponseConstant.RESULT, responseBody);
        return prepareResponse(code, msgKey, HttpStatus.OK, dataBody);
    }

    protected ResponseEntity<Map<String, Object>> prepareErrorResponse(int code, String msg, Object... args) {
        Map<String, Object> responseBody = new HashMap<String, Object>();
        return prepareResponse(code, msg, HttpStatus.OK, responseBody);
    }

    private ResponseEntity<Map<String, Object>> prepareResponse(int code, String msgKey, HttpStatus status,
                                                                Map<String, Object> responseBody) {
        String message = msgKey;
        responseBody.put(ResponseConstant.STATUS, new Status(code, message));
        String uuid = RequestContext.getContext().getGeneratedToken();
        if (!StringUtils.isEmpty(uuid)) {
            monitorLogger.info("RESPONSE -> {}", uuid + "  " + responseBody.toString());
        }
        return new ResponseEntity<>(responseBody, status);
    }
}