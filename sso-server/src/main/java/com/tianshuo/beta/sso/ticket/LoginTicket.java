package com.tianshuo.beta.sso.ticket;

import com.tianshuo.beta.sso.model.User;

import java.util.List;

/**
 * 登录票据
 *
 * @author tianshuo
 */
public interface LoginTicket extends Ticket {

    String prx = "TGT";

    /**
     * 生成服务票据
     *
     * @param loginTicket
     * @param service
     * @return
     */
    ServiceTicket generateServiceTicket(LoginTicket loginTicket, String service);

    /**
     * 获取服务票据列表
     * @return List<ServiceTicket>
     */
    List<ServiceTicket> getServiceTicketList();


    /**
     * 获取用户信息
     *
     * @return
     */
    User getUserInfo();

}
