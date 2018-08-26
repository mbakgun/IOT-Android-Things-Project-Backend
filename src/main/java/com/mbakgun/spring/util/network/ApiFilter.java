package com.mbakgun.spring.util.network;


import com.mbakgun.spring.service.RedisService.RedisService;
import com.mbakgun.spring.util.CommonFunction;
import com.mbakgun.spring.util.constants.GenericConstant;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;;
import javax.servlet.http.HttpServletResponse;

import static com.mbakgun.spring.util.constants.GenericConstant.HEADER_TOKEN;


@Component
public class ApiFilter extends GenericFilterBean {
    private RedisService redisService;
    private Logger monitorLogger = LoggerFactory.getLogger("monitorLogger");

    public ApiFilter(RedisService redisService) {
        this.redisService = redisService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        GenericRequestWrapper genericRequestWrapper = new GenericRequestWrapper(httpServletRequest);
        String GENERATED_TOKEN = httpServletRequest.getHeader(HEADER_TOKEN);
        boolean generatedTokenEmptyOrNull = StringUtils.isEmpty(GENERATED_TOKEN);
        boolean generatedTokenNotValid = !redisService.isExist(GenericConstant.REDIS_PREFIX_TOKEN, GENERATED_TOKEN);
        if (generatedTokenEmptyOrNull || generatedTokenNotValid) {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setContentType("application/json");
            String additionalInfo = "401 Required headers NOT SPECIFIED in the request";
            if (!generatedTokenEmptyOrNull) {
                additionalInfo = "401 Required headers NOT VALID(" + GENERATED_TOKEN + ") in the request";
            }
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, additionalInfo);
            logMonitorFromFilter(genericRequestWrapper, additionalInfo);
            return;
        } else {
            org.slf4j.MDC.put("generatedToken", GENERATED_TOKEN);
            logMonitorFromFilter(genericRequestWrapper, GENERATED_TOKEN);
        }
        chain.doFilter(genericRequestWrapper, response);
    }

    private void logMonitorFromFilter(GenericRequestWrapper genericRequestWrapper, String additionalInfo) throws IOException {
        String method = genericRequestWrapper.getMethod();
        String defaultInformation = additionalInfo + "  " + method + "  " + genericRequestWrapper.getRequestURI() + "  " + CommonFunction.getClientIp(genericRequestWrapper);
        if (method.equals("POST")) {
            monitorLogger.info("REQUEST -> {}", defaultInformation + "  " + IOUtils.toString(genericRequestWrapper.getReader()).replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll(" ", ""));
        } else {
            monitorLogger.info("REQUEST -> {}", defaultInformation);
        }
    }
}