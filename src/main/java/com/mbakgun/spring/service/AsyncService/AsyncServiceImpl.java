package com.mbakgun.spring.service.AsyncService;


import com.mbakgun.spring.model.Notification;
import com.mbakgun.spring.service.AbstractServiceImpl;
import com.mbakgun.spring.util.CommonFunction;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

@Service
public class AsyncServiceImpl extends AbstractServiceImpl implements AsyncService {

    @Value("${notification.title}")
    private String title;

    @Value("${notification.authorization}")
    private String authorization;

    @Async("processExecutor")
    public void sendNotificationToServerAsync(byte[] byteImageArray, String path, String fileName, String extension, Notification notification) {
        new File(path).mkdirs();
        extension = StringUtils.isEmpty(extension) ? "png" : extension;
        try {
            if (byteImageArray != null && byteImageArray.length > 0) {
                File imageFile = CommonFunction.writeFileWithBytes(byteImageArray, path, fileName);
                BufferedImage logoImage = ImageIO.read(imageFile);
                ImageIO.write(logoImage, extension, new File(path, fileName));
                CommonFunction.sendNotification(notification.getSentTo(), title, notification.getName(), notification.getImageUrl(), authorization);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Async("processExecutor")
    @Override
    public void deleteFileAsync(String path) {
        File file = new File(path);
        logger.info((String.format("Del(%s) : ", path) + file.delete()));
    }
}