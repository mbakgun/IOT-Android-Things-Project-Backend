package com.mbakgun.spring.util.network;


import com.mbakgun.spring.util.entity.Platform;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import static com.mbakgun.spring.util.constants.GenericConstant.HEADER_PLATFORM;
import static com.mbakgun.spring.util.constants.GenericConstant.HEADER_TOKEN;


@Component
public class HeaderFilter extends GenericFilterBean {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        GenericRequestWrapper genericRequestWrapper = new GenericRequestWrapper(httpServletRequest);
        RequestContext context = RequestContext.getContext();
        String platformStr = httpServletRequest.getHeader(HEADER_PLATFORM);
        if (!StringUtils.isEmpty(platformStr)) {
            context.setPlatform(Platform.getEnum(platformStr));
        }
        String generatedToken = httpServletRequest.getHeader(HEADER_TOKEN);
        if (!StringUtils.isEmpty(generatedToken)) {
            context.setGeneratedToken(generatedToken);
        }
        chain.doFilter(genericRequestWrapper, response);
    }
}
