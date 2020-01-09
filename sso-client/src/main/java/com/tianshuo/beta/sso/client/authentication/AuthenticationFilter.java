package com.tianshuo.beta.sso.client.authentication;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.tianshuo.beta.sso.client.dto.Result;
import com.tianshuo.beta.sso.client.session.SessionHandler;
import com.tianshuo.beta.sso.client.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 客户端校验
 *
 * @author tianshuo
 */
public class AuthenticationFilter implements Filter {

    private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);


    private final String USER_KEY = "user";

    /**
     * 表达式 用于不需要登录的资源过滤
     */
    private String pattern;

    /**
     * 匹配类型 内置：正则表达式和包含路径策略
     */
    private String patternType;

    /**
     * 登录路径
     */
    private String ssoServerUrlPrefix;

    /**
     * 路径策略
     */
    private static final Map<String, Class<? extends UrlPatternMatcherStrategy>> URL_PATTER_TYPE = new HashMap<>();

    /**
     * 忽略路径规则实现类
     */
    private UrlPatternMatcherStrategy ignoreUrlPatternMatcherStrategyClass = null;


    static {
        URL_PATTER_TYPE.put("regex", RegexUrlPatternMatcherStrategy.class);
        URL_PATTER_TYPE.put("contain", ContainUrlPatternMatcherStrategy.class);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        pattern = filterConfig.getInitParameter("pattern");
        ssoServerUrlPrefix = filterConfig.getInitParameter("ssoServerUrlPrefix");
        patternType = filterConfig.getInitParameter("patternType");

        if (StringUtils.isNotEmpty(patternType)) {
            Class<? extends UrlPatternMatcherStrategy> urlPatternMatcherStrategyClass = URL_PATTER_TYPE.get(patternType);
            try {
                if (urlPatternMatcherStrategyClass != null) {

                    ignoreUrlPatternMatcherStrategyClass = (UrlPatternMatcherStrategy) Class.forName(urlPatternMatcherStrategyClass.getName())
                            .newInstance();
                }
                if (ignoreUrlPatternMatcherStrategyClass != null) {
                    ignoreUrlPatternMatcherStrategyClass.setPattern(pattern);
                }
            } catch (InstantiationException e) {
                LOGGER.error("{}", e);
            } catch (IllegalAccessException e) {
                LOGGER.error("{}", e);
            } catch (ClassNotFoundException e) {
                LOGGER.error("{}", e);
            }


        }

    }


    /**
     * 校验成功回调方法
     *
     * @param request   HttpServletRequest
     * @param response  HttpServletResponse
     * @param assertion 用户信息
     */
    protected void onSuccessfulValidation(final HttpServletRequest request, final HttpServletResponse response,
                                          final User assertion) {
        // nothing to do here.
    }

    /**
     * 校验失败回调方法
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    protected void onFailedValidation(final HttpServletRequest request, final HttpServletResponse response) {
        // nothing to do here.
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final HttpSession session = request.getSession();
        //不需要登录的资源直接通过
        if (isRequestUrlExcluded(request)) {
            filterChain.doFilter(request, response);
            return;
        }


        //单点登出
        String logout = request.getParameter("logout");
        String ticketId = request.getParameter("ticket");

        if (StringUtils.isNotEmpty(logout) && StringUtils.isNotEmpty(ticketId)) {
            LOGGER.debug("收到退出通知，退出。");
            SessionHandler.removeSession(ticketId);
            response.sendRedirect(ssoServerUrlPrefix + "/login");
            return;
        }


        String clientUrl = request.getRequestURL().toString();
        User user = (User) session.getAttribute(USER_KEY);
        if (user != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        if (StringUtils.isNotEmpty(ticketId)) {
            LOGGER.debug("票据信息：" + ticketId);
            String url = ssoServerUrlPrefix + "/ticketValidate?ticket=" + ticketId;
            try {
                HttpUtil client = new HttpUtil(url);
                client.call();
                Result<User> result = JSON.parseObject(client.getResponse(), new TypeReference<Result<User>>() {
                });
                if (result.isSuccess()) {
                    if (result.getData() instanceof User) {
                        User userInfo = result.getData();
                        session.setAttribute(USER_KEY, userInfo);
                        SessionHandler.addSession(ticketId, session);
                        onSuccessfulValidation(request, response, user);
                    }

                    filterChain.doFilter(servletRequest, servletResponse);
                    return;
                } else {
                    response.sendRedirect(ssoServerUrlPrefix + "/login?clientUrl=" + clientUrl);
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
                onFailedValidation(request, response);
                response.sendRedirect(ssoServerUrlPrefix + "/login?clientUrl=" + clientUrl);
                return;
            }

        }

        response.sendRedirect(ssoServerUrlPrefix + "/login?clientUrl=" + clientUrl);
        return;
    }

    @Override
    public void destroy() {

    }

    /**
     * 路径匹配规则包含
     *
     * @param request HttpServletRequest
     * @return boolean
     */
    private boolean isRequestUrlExcluded(final HttpServletRequest request) {
        /**
         * 如果url策略类的实现为空，就直接返回false必须校验是否登录
         */
        if (ignoreUrlPatternMatcherStrategyClass == null) {
            return false;
        }

        final StringBuffer urlBuffer = request.getRequestURL();
        if (request.getQueryString() != null) {
            urlBuffer.append("?").append(request.getQueryString());
        }
        final String requestUri = urlBuffer.toString();
        return matches(requestUri);
    }

    private boolean matches(final String url) {
        return ignoreUrlPatternMatcherStrategyClass.matches(url);
    }


}
