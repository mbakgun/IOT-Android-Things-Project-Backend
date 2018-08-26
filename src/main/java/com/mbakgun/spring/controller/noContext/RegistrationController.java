package com.mbakgun.spring.controller.noContext;

import com.mbakgun.spring.controller.AbstractController;
import com.mbakgun.spring.model.Device;
import com.mbakgun.spring.util.constants.ResponseConstant;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import static com.mbakgun.spring.util.constants.ControllerConstant.REGISTRATION;
import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_DEVICE;
import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_TOKEN;
import static com.mbakgun.spring.util.constants.RequestConstant.DEVICE_ID;
import static com.mbakgun.spring.util.constants.RequestConstant.DEVICE_NAME;


@RestController
public class RegistrationController extends AbstractController {

    @GetMapping(REGISTRATION)
    @ResponseBody
    public Object registerDeviceRequest(HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap();
        String deviceId = request.getParameter(DEVICE_ID);
        String name = request.getParameter(DEVICE_NAME);
        Device device = new Device(deviceId != null ? deviceId : "test", name != null ? name : "");
        if (redisService.isExist(REDIS_PREFIX_DEVICE, deviceId)) {
            logger.info("DEVICE ID EXIST:" + deviceId);
            responseBody.put(ResponseConstant.DEVICE, device.getDeviceId());
            return prepareSuccessResponse(HttpStatus.OK.value(), ResponseConstant.DEVICE + " exist",
                    responseBody);
        } else {
            String tokenGeneratedFirstTime = UUID.randomUUID().toString();
            device.setGeneratedToken(tokenGeneratedFirstTime);
            redisService.saveObject(device, REDIS_PREFIX_DEVICE, deviceId);
            redisService.saveValue(REDIS_PREFIX_TOKEN, tokenGeneratedFirstTime, deviceId);
            responseBody.put(ResponseConstant.DEVICE, device);
            return prepareSuccessResponse(HttpStatus.OK.value(), ResponseConstant.SUCCESS_MESSAGE,
                    responseBody);
        }
    }
}