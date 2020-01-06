package com.tianshuo.beta.sso.util;

import com.tianshuo.beta.sso.constant.GlobalConstant;
import com.tianshuo.beta.sso.ticket.Ticket;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共工具类
 */
public class CommonUtil {

    /**
     * 构造clietUrl
     *
     * @param request
     * @return
     */
    public static String constructClientUrl(HttpServletRequest request) {
        String url = request.getParameter(GlobalConstant.CLIENT_KEY);
        if (StringUtils.isEmpty(url)) {
            return null;
        }
        return url;
    }

    /**
     * 构造跳转URL
     *
     * @param ticket
     * @param clientUrl
     * @return
     */
    public static String constructUrlRedirectTo(Ticket ticket, String clientUrl) {
        return clientUrl += (clientUrl.contains("?") ? "&" : "?") + "ticket=" + ticket.getId();
    }

}
