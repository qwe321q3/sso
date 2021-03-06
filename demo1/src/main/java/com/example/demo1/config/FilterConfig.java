package com.example.demo1.config;

import com.tianshuo.beta.sso.client.authentication.AuthenticationFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author tianshuo
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new AuthenticationFilter());
        Map<String, String> initMap = new HashMap<>();
        initMap.put("pattern", "/js/*|/img/*|/static/*|/css/*|/test/*");
        initMap.put("patternType", "regex");
        initMap.put("ssoServerUrlPrefix", "http://localhost");
        bean.setInitParameters(initMap);
        bean.addUrlPatterns("/*");
        bean.setOrder(5);
        return bean;
    }


}