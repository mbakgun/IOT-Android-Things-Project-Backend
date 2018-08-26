package com.mbakgun.spring.controller.noContext;

import com.google.gson.reflect.TypeToken;
import com.mbakgun.spring.controller.AbstractController;
import com.mbakgun.spring.model.Device;
import com.mbakgun.spring.request.PostAuthRequest;
import com.mbakgun.spring.util.constants.ResponseConstant;
import com.mbakgun.spring.util.exception.MbaException;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import static com.mbakgun.spring.util.constants.ControllerConstant.AUTH;
import static com.mbakgun.spring.util.constants.ControllerConstant.CONTROLLER_HEADERS;
import static com.mbakgun.spring.util.constants.ControllerConstant.CONTROLLER_JSON_PRODUCES;
import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_DEVICE;


@RestController
public class AuthController extends AbstractController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = AUTH, headers = CONTROLLER_HEADERS, produces = CONTROLLER_JSON_PRODUCES)
    public Object authRequest(@RequestBody String requestBody, HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap();
        try {
            String deviceId = PostAuthRequest.convertRequestBodyToPostAuthRequestModel(requestBody).getDeviceId();
            if (redisService.isExist(REDIS_PREFIX_DEVICE, deviceId)) {
                Device device = redisService.getObject(REDIS_PREFIX_DEVICE, deviceId, TypeToken.get(Device.class));
                String fcmToken = PostAuthRequest.convertRequestBodyToPostAuthRequestModel(requestBody).getFcmToken();
                if (!StringUtils.isEmpty(fcmToken)) {
                    device.addFcmTokenList(fcmToken);
                    redisService.saveObject(device, REDIS_PREFIX_DEVICE, deviceId);
                }
                responseBody.put(ResponseConstant.DEVICE, device);
            } else {
                logger.info("DEVICE ID NOT EXIST:" + deviceId);
                return prepareErrorResponse(401, "unauthorized");
            }
        } catch (MbaException e) {
            logger.error(e.getMessage(), e);
            return prepareErrorResponse(401, "unauthorized");
        }
        return prepareSuccessResponse(HttpStatus.OK.value(), ResponseConstant.SUCCESS_MESSAGE,
                responseBody);
    }
}