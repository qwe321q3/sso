package com.tianshuo.beta.sso.client.authentication;

import com.tianshuo.beta.sso.client.util.CookieUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 客户端校验
 *
 * @author tianshuo
 */
public class AuthenticationFilter implements Filter {

    /**
     * 表达式 用于不需要登录的资源过滤
     */
    private String pattern;

    /**
     * 登录路径
     */
    private String loginUrl;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pattern = filterConfig.getInitParameter("pattern");
        loginUrl = filterConfig.getInitParameter("loginUrl");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //不需要登录的资源直接通过
        if(isRequestUrlExcluded(request)){
            filterChain.doFilter(request,response);
        }
        String tgc = CookieUtil.getCookieValueByName(request, CookieUtil.TGC_KEY);
        if(StringUtils.isEmpty(tgc)){
           StringBuffer clientUrl =  request.getRequestURL();
            response.sendRedirect(loginUrl+"clientUrl="+clientUrl.toString());
        }

    }

    @Override
    public void destroy() {

    }


    private boolean isRequestUrlExcluded(final HttpServletRequest request) {
        final StringBuffer urlBuffer = request.getRequestURL();
        if (request.getQueryString() != null) {
            urlBuffer.append("?").append(request.getQueryString());
        }
        final String requestUri = urlBuffer.toString();
        return matches(requestUri);
    }

    private boolean matches(final String url) {
        return url.contains(this.pattern);
    }


}
