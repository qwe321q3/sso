package com.tianshuo.beta.sso.client.authentication;

/**
 * 路径匹配策略
 *
 * @author tianshuo
 */
public interface UrlPatternMatcherStrategy {

    /**
     * 匹配方法
     *
     * @param url
     * @return
     */
    boolean matches(String url);

    /**
     * 需要匹配的规则
     *
     * @param pattern
     */
    void setPattern(String pattern);
}