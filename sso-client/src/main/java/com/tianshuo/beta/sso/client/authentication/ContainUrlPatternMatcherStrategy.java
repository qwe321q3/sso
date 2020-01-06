package com.tianshuo.beta.sso.client.authentication;

/**
 * 包含路径匹配策略
 *
 * @author tianshuo
 */
public class ContainUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {


    private String pattern;

    public ContainUrlPatternMatcherStrategy() {
    }


    @Override
    public boolean matches(String url) {
        return url.contains(pattern);
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
}