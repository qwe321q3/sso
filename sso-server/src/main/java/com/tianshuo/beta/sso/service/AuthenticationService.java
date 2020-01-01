package com.tianshuo.beta.sso.service;

import com.tianshuo.beta.sso.model.User;

/**
 * 认证接口
 */
public interface AuthenticationService {

    /**
     * 登录接口
     * @param user
     * @return
     */
    User login(User user);

    /**
     * 登出接口
     * @return
     */
    boolean logout();

    /**
     * 票据校验接口
     * @param ticket
     * @return
     */
    User validate(String ticket);

}
