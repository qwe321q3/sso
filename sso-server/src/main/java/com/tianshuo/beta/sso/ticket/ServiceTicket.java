package com.tianshuo.beta.sso.ticket;

/**
 * 登录票据
 *
 * @author tianshuo
 */
public interface ServiceTicket extends Ticket {

    String prx = "ST";

    /**
     * 获取登录票据
     *
     * @return
     */
    LoginTicket getLoginTicket();

    /**
     * 获取服务信息/客户端url地址
     *
     * @return 客户端url地址
     */
    String getService();

}
