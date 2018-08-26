package com.mbakgun.spring.controller.api;

import com.google.gson.reflect.TypeToken;
import com.mbakgun.spring.controller.AbstractController;
import com.mbakgun.spring.model.Device;
import com.mbakgun.spring.model.Notification;
import com.mbakgun.spring.service.AsyncService.AsyncService;
import com.mbakgun.spring.util.CommonFunction;
import com.mbakgun.spring.util.constants.ResponseConstant;
import com.mbakgun.spring.util.network.RequestContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.activation.MimeTypeParseException;
import javax.servlet.http.HttpServletRequest;

import static com.mbakgun.spring.util.constants.ControllerConstant.CONTROLLER_HEADERS;
import static com.mbakgun.spring.util.constants.ControllerConstant.CONTROLLER_JSON_PRODUCES;
import static com.mbakgun.spring.util.constants.ControllerConstant.GET_NOTIFICATION_LIST;
import static com.mbakgun.spring.util.constants.ControllerConstant.SEND_NOTIFICATION;
import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_DEVICE;
import static com.mbakgun.spring.util.constants.GenericConstant.REDIS_PREFIX_NOTIFICATION;
import static com.mbakgun.spring.util.constants.ResponseConstant.NOTIFICATION_LIST;

@RestController
public class NotificationController extends AbstractController {
    @Value("${io.file.path}")
    private String path;

    @Value("${image.url.prefix}")
    private String imageUrlPrefix;

    @Autowired
    private AsyncService asyncService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = SEND_NOTIFICATION, headers = CONTROLLER_HEADERS, produces = CONTROLLER_JSON_PRODUCES, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object sendNotification(@RequestParam("img") MultipartFile image) {
        Map<String, Object> responseBody = new HashMap();
        RequestContext context = RequestContext.getContext();
        String generatedToken = context.getGeneratedToken();
        String deviceId = redisService.getDeviceId(generatedToken);
        if (StringUtils.isEmpty(deviceId)) {
            logger.info("DEVICE ID NOT EXIST:" + generatedToken);
            return prepareErrorResponse(401, "unauthorized");
        } else {
            Device device = redisService.getObject(REDIS_PREFIX_DEVICE, deviceId, TypeToken.get(Device.class));
            if (device.isActive() && !device.getFcmTokenList().isEmpty()) {
                try {
                    String extension = CommonFunction.getFileExtension(image.getContentType());
                    Date now = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
                    String fileNameDb = deviceId + '@' + formatter.format(now);
                    String fileNameUrl = deviceId + '@' + String.valueOf(now.getTime());
                    String fileName = fileNameUrl + '.' + extension;
                    String imageUrl = String.format(imageUrlPrefix, fileName);
                    Notification notification = new Notification(now, imageUrl, device);
                    asyncService.sendNotificationToServerAsync(image.getBytes(), path, fileName, extension, notification);
                    redisService.saveObject(notification, REDIS_PREFIX_NOTIFICATION, fileNameDb);
                    logger.info("Notification Sent");
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    return prepareErrorResponse(HttpStatus.BAD_GATEWAY.value(), e.getMessage());
                } catch (MimeTypeParseException e) {
                    logger.error(e.getMessage(), e);
                    return prepareErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage());
                }
            } else {
                logger.info("Device is off OR not bind any device , notification wont sent");
                return prepareErrorResponse(409, "device is inactive");
            }
        }
        return prepareSuccessResponse(HttpStatus.OK.value(), ResponseConstant.SUCCESS_MESSAGE,
                responseBody);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = GET_NOTIFICATION_LIST, headers = CONTROLLER_HEADERS, produces = CONTROLLER_JSON_PRODUCES)
    public Object getNotificationListRequest(HttpServletRequest request) {
        Map<String, Object> responseBody = new HashMap();
        RequestContext context = RequestContext.getContext();
        String generatedToken = context.getGeneratedToken();
        String deviceId = redisService.getDeviceId(generatedToken);
        if (StringUtils.isEmpty(deviceId)) {
            logger.info("DEVICE ID NOT EXIST:" + generatedToken);
            return prepareErrorResponse(401, "unauthorized");
        } else {
            Map<String, Object> notificationMap = redisService.getMapForKey(REDIS_PREFIX_NOTIFICATION, deviceId, TypeToken.get(Notification.class));
            if (!notificationMap.isEmpty()) {
                ArrayList notificationList = new ArrayList(notificationMap.values());
                responseBody.put(NOTIFICATION_LIST, notificationList);
            }
        }
        return prepareSuccessResponse(HttpStatus.OK.value(), ResponseConstant.SUCCESS_MESSAGE,
                responseBody);
    }
}