package com.tianshuo.beta.sso.client.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tianshuo.beta.sso.client.dto.Result;
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
public class AuthenticationFilter implements Filter{


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

    /**
     * 校验成功回调方法
     * @param request
     * @param response
     * @param assertion
     */
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response,
                                          final User assertion) {
        // nothing to do here.
    }

    /**
     * 校验失败回调方法
     * @param request
     * @param response
     */
    protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
        // nothing to do here.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final HttpSession session = request.getSession(true);
        //不需要登录的资源直接通过
        if(isRequestUrlExcluded(request)){
            filterChain.doFilter(request,response);
        }


        //单点登出
        String logout = request.getParameter("logOut");
        if(StringUtils.isNotEmpty(logout)){
            System.out.println("收到退出通知，退出。");
            session.invalidate();
            response.sendRedirect(casServerUrlPrefix+"/login");
            return;
        }


        String clientUrl =  request.getRequestURL().toString();


        User user = (User) session.getAttribute(USER_KEY);
        System.out.println("sessionId:  "+session.getId());
        System.out.println("session: "+user);
        if(user!=null){
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String ticketId = request.getParameter("ticket");

        if(StringUtils.isNotEmpty(ticketId)){
            System.out.println("票据信息："+ticketId);
            String url = casServerUrlPrefix+"/ticketValidate?ticket="+ticketId;
            try {
                HttpUtil client = new HttpUtil(url);
                client.call();
                Result<User> result = JSON.parseObject(client.getResponse(),new TypeReference<Result<User>>() {});
                if(result.isSuccess()){
                    if(result.getData() instanceof User){
                        User userInfo = result.getData();
                        session.setAttribute(USER_KEY,userInfo);
                        onSuccessfulValidation(request, response, user);
                    }

                    filterChain.doFilter(servletRequest, servletResponse);
                    return ;
                }else{
                    response.sendRedirect(casServerUrlPrefix+"/validateLogin?clientUrl="+clientUrl);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailedValidation(request, response);
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

    /**
     * 路径匹配规则包含
     * @param request
     * @return
     */
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
