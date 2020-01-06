package com.tianshuo.beta.sso.client.authentication;

import java.util.regex.Pattern;

/**
 * 正则表达式路径匹配策略
 *
 * @author tianshuo
 */
public class RegexUrlPatternMatcherStrategy implements UrlPatternMatcherStrategy {

    private Pattern pattern;

    public RegexUrlPatternMatcherStrategy() {
    }

    @Override
    public boolean matches(String url) {
        return this.pattern.matcher(url).find();
    }

    @Override
    public void setPattern(String pattern) {
        this.pattern = Pattern.compile(pattern);
    }
}