package com.tianshuo.beta.sso.client.authentication;

import com.alibaba.fastjson.JSON;
import com.tianshuo.beta.sso.client.dto.Result;
import com.tianshuo.beta.sso.client.util.CookieUtil;
import com.tianshuo.beta.sso.client.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 客户端校验
 *
 * @author tianshuo
 */
public class AuthenticationFilter implements Filter {

    private final String USER_KEY = "user";

    /**
     * 表达式 用于不需要登录的资源过滤
     */
    private String pattern;

    /**
     * 登录路径
     */
    private String casServerUrlPrefix;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        pattern = filterConfig.getInitParameter("pattern");
        casServerUrlPrefix = filterConfig.getInitParameter("casServerUrlPrefix");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //不需要登录的资源直接通过
        if(isRequestUrlExcluded(request)){
            filterChain.doFilter(request,response);
        }
        String clientUrl =  request.getRequestURL().toString();

        final HttpSession session = request.getSession(true);

        User user = (User) session.getAttribute(USER_KEY);
        System.out.println("session: "+user);
        if(user!=null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String ticketId = request.getParameter("ticket");

        if(StringUtils.isNotEmpty(ticketId)){
            String url = casServerUrlPrefix+"/ticketValidate?ticket="+ticketId;
            HttpUtil client = new HttpUtil(url);
            client.call();
            Result<User> result = (Result<User>) JSON.parseObject(client.getResponse(),Result.class);
            System.out.println("用户信息："+result.getData());
            System.out.println(clientUrl);
            if(result.isSuccess()){
                session.setAttribute(USER_KEY,result.getData());
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }else{
                response.sendRedirect(casServerUrlPrefix+"/login?clientUrl="+clientUrl);
                return;
            }

        }

        response.sendRedirect(casServerUrlPrefix+"/login?clientUrl="+clientUrl);
        return;
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
