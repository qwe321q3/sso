package com.tianshuo.beta.sso.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 通用配置
 * 1、配置默认页面
 * 2、配置swagger请求页面
 * @author tianshuo
 */
@Configuration
public class ResourcesConfig implements WebMvcConfigurer {

    /**
     * 配置默认页面
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("login");
    }

}
