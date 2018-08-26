package com.mbakgun.spring.controller.api;

import com.google.gson.reflect.TypeToken;
import com.mbakgun.spring.controller.AbstractController;
import com.mbakgun.spring.model.Device;
import com.mbakgun.spring.request.PostConfigureRequest;
import com.mbakgun.spring.util.constants.ResponseConstant;
import com.mbakgun.spring.util.exception.MbaException;
import com.mbakgun.spring.util.network.RequestContext;

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

import static com.mbakgun.spring.util.constants.ControllerConstant.CONTROLLER_HEADERS;
import static com.mbakgun.spring.util.constants.ControllerConstant.CONTROLLER_JSON_PRODUCES;
import static com.mbakgun.spring.util.constants.ControllerConstant.SET_CONFIGURATION;
import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_DEVICE;

@RestController
public class ConfigurationController extends AbstractController {

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = SET_CONFIGURATION, headers = CONTROLLER_HEADERS, produces = CONTROLLER_JSON_PRODUCES)
    public Object controlRequest(@RequestBody String requestBody, HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap();
        RequestContext context = RequestContext.getContext();
        String generatedToken = context.getGeneratedToken();
        String deviceId = redisService.getDeviceId(generatedToken);
        if (StringUtils.isEmpty(deviceId)) {
            logger.info("DEVICE ID NOT EXIST:" + generatedToken);
            return prepareErrorResponse(401, "unauthorized");
        } else {
            try {
                boolean activated = PostConfigureRequest.convertRequestBodyToPostConfigureRequestModel(requestBody).isActive();
                String name = PostConfigureRequest.convertRequestBodyToPostConfigureRequestModel(requestBody).getName();
                Device device = redisService.getObject(REDIS_PREFIX_DEVICE, deviceId, TypeToken.get(Device.class));
                if (!StringUtils.isEmpty(name)) {
                    device.setName(name);
                }
                device.setActive(activated);
                redisService.saveObject(device, REDIS_PREFIX_DEVICE, deviceId);
                responseBody.put(ResponseConstant.DEVICE, device);
            } catch (MbaException e) {
                logger.error(e.getMessage(), e);
                return prepareErrorResponse(400, "bad request");
            }
        }
        return prepareSuccessResponse(HttpStatus.OK.value(), ResponseConstant.SUCCESS_MESSAGE,
                responseBody);
    }
}