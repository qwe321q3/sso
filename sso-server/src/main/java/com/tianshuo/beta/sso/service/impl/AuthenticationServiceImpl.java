package com.tianshuo.beta.sso.service.impl;

import com.tianshuo.beta.sso.aop.exception.LoginException;
import com.tianshuo.beta.sso.dao.UserMapper;
import com.tianshuo.beta.sso.model.User;
import com.tianshuo.beta.sso.model.UserExample;
import com.tianshuo.beta.sso.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 登录接口
     *
     * @param user
     * @return
     */
    public User login(User user) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(user.getLoginName());
        criteria.andPasswordEqualTo(user.getPassword());
        criteria.andStatusEqualTo("0");
        List<User> userList = userMapper.selectByExample(userExample);
        if(userList!=null&&userList.size()>0){

            User userInfo = userList.get(0);
            return userInfo;
        }

        throw new LoginException("账号或密码错误");
    }

    /**
     * 登出接口
     *
     * @return
     */
    public boolean logout() {
        return true;
    }

    /**
     * 票据校验接口
     *
     * @param ticket
     * @return
     */
    public User validate(String ticket) {
        return null;
    }

}
