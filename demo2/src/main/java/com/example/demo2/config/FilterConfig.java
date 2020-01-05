package com.example.demo2.config;

import com.tianshuo.beta.sso.client.authentication.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new AuthenticationFilter());
        Map<String, String> initMap = new HashMap<>();
        initMap.put("pattern","jsp");
        initMap.put("casServerUrlPrefix","http://localhost");
        bean.setInitParameters(initMap);
        bean.addUrlPatterns("/*");
        return bean;
    }
}