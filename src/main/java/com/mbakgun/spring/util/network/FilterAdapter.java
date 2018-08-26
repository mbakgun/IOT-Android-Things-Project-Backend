package com.mbakgun.spring.util.network;

import com.mbakgun.spring.service.RedisService.RedisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import static com.mbakgun.spring.util.constants.ControllerConstant.TOKEN_CONTROLLER_PATTERN;

@Configuration
public class FilterAdapter extends WebMvcConfigurerAdapter {
    @Autowired
    private RedisService redisService;

    @Bean
    public FilterRegistrationBean HeaderFilter() {
        // header for all request
        FilterRegistrationBean headerRegistration = new FilterRegistrationBean();
        headerRegistration.setFilter(new HeaderFilter());
        headerRegistration.addUrlPatterns("/*");
        return headerRegistration;
    }

    @Bean
    public FilterRegistrationBean ApiFilter() {
        // secure all request with generatedToken
        FilterRegistrationBean apiRegistration = new FilterRegistrationBean();
        apiRegistration.setFilter(new ApiFilter(redisService));
        apiRegistration.addUrlPatterns(TOKEN_CONTROLLER_PATTERN + "/*");
        return apiRegistration;
    }
}