package com.tianshuo.beta.sso.service;

import com.tianshuo.beta.sso.model.User;

/**
 * 认证接口
 */
public interface AuthenticationService {

    /**
     * 登录接口
     *
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 登出接口
     *
     * @param ticketId
     * @return
     */
    boolean logout(String ticketId);

    /**
     * 票据校验接口
     *
     * @param ticketId
     * @return
     */
    User validate(String ticketId);

    /**
     * 校验登录票是否存在
     *
     * @param loginTicketId
     * @return
     */
    boolean tgtValidate(String loginTicketId);

}
