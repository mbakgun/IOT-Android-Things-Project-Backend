package com.mbakgun.spring.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.activation.MimeType;
import javax.activation.MimeTypeParseException;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by burakakgun on 7.04.2018.
 */

public class CommonFunction {

    private static String url = "http://fcm.googleapis.com/fcm/send";

    public static File writeFileWithBytes(byte[] byteImageArray, String path, String fileName) throws IOException {
        if (byteImageArray != null && byteImageArray.length > 0) {
            File tempFile = new File(path, fileName);
            tempFile.createNewFile();
            FileOutputStream tempOut = new FileOutputStream(tempFile);
            tempOut.write(byteImageArray);
            tempOut.close();
            return tempFile;
        }
        return null;
    }

    public static String getFileExtension(String contentType) throws MimeTypeParseException {
        MimeType mime = new MimeType(contentType);
        return mime.getSubType();
    }

    public static String getClientIp(HttpServletRequest request) {
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }

    public static String getRegistrationIds(List<String> tokenList) {
        String value = "";
        for (String item : tokenList) {
            value = value + "\"" + item + "\",";
        }
        return value.substring(0, value.length() - 1);
    }

    public static void sendNotification(List<String> tokenList, String title, String body, String imageURL, String authorization) throws Exception {
        String param = String.format(
                "{ \"registration_ids\": [%s] , \"data\": { \"title\" : \"%s\" , \"body\" : \"%s\" ,\"imageURL\" : \"%s\"  } }",
                getRegistrationIds(tokenList), title, body, imageURL);
        URLConnection connection = new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.setRequestProperty("Authorization", authorization);
        byte[] bytes = param.getBytes("utf-8");
        OutputStream out = connection.getOutputStream();
        try {
            out.write(bytes);
        } finally {
            out.close();
        }
       /* InputStream response = connection.getInputStream();
        BufferedReader in = new BufferedReader(new
                InputStreamReader(response));
        String line = null;
        while ((line = in.readLine()) != null) {
            System.out.println(line);
        }*/
    }
}