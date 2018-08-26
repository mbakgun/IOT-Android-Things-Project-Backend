package com.mbakgun.spring.util.network;

import com.mbakgun.spring.util.entity.Platform;

public class RequestContext {
    private static final ThreadLocal<RequestContext> CONTEXT = new ThreadLocal<>();
    private String generatedToken;
    private Platform platform;

    public static RequestContext getContext() {
        RequestContext result = CONTEXT.get();
        if (result == null) {
            result = new RequestContext();
            CONTEXT.set(result);
        }
        return result;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public Platform getPlatform() {
        if (platform != null) {
            return platform;
        }
        return Platform.NULL;
    }

    public String getGeneratedToken() {
        return generatedToken;
    }

    public void setGeneratedToken(String generatedToken) {
        this.generatedToken = generatedToken;
    }
}