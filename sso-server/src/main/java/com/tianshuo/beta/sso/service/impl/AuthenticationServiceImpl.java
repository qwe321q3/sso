package com.tianshuo.beta.sso.service.impl;

import com.tianshuo.beta.sso.aop.exception.LoginException;
import com.tianshuo.beta.sso.dao.UserMapper;
import com.tianshuo.beta.sso.model.User;
import com.tianshuo.beta.sso.model.UserExample;
import com.tianshuo.beta.sso.service.AuthenticationService;
import com.tianshuo.beta.sso.ticket.LoginTicket;
import com.tianshuo.beta.sso.ticket.ServiceTicket;
import com.tianshuo.beta.sso.ticket.TicketException;
import com.tianshuo.beta.sso.ticket.registry.TicketRegistry;
import com.tianshuo.beta.sso.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 验证服务类
 *
 * @author tianshuo
 */
@Slf4j
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private TicketRegistry ticketRegistry;

    /**
     * 登录接口
     *
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        if(StringUtils.isEmpty(user.getLoginName())||StringUtils.isEmpty(user.getPassword())){
            return null;
        }
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(user.getLoginName());
        criteria.andPasswordEqualTo(user.getPassword());
        criteria.andStatusEqualTo("0");
        List<User> userList = userMapper.selectByExample(userExample);
        if (userList != null && userList.size() > 0) {

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
    @Override
    public boolean logout(String ticketId) {
        if (log.isDebugEnabled()) {
            log.debug("logout ticket [{}] ..", ticketId);
        }

        //发送业务系统登出指令
        LoginTicket loginTicket = (LoginTicket) ticketRegistry.getTicket(ticketId);

        List<ServiceTicket> serviceTicketList = loginTicket.getServiceTicketList();
        // TODO: 2020/1/7 后续优化成异步操作 临时try catch解决
        for (ServiceTicket serviceTicket : serviceTicketList) {

            try {
                HttpUtil client = new HttpUtil(serviceTicket.getService());
                client.setRequest("logout=logout&ticket=" + serviceTicket.getId());
                client.call();
            } catch (Exception e) {
                log.error("{}", e);
                continue;
            }
        }

        //删除登录key
        ticketRegistry.deleteTicket(ticketId);


        return true;
    }

    /**
     * 票据校验接口
     * 1、服务票据一次失效
     *
     * @param serviceTicketId
     * @return
     */
    @Override
    public User validate(String serviceTicketId) {
        if (log.isDebugEnabled()) {
            log.debug("validate ticket [{}] ..", serviceTicketId);
        }
        ServiceTicket serviceTicket = (ServiceTicket) ticketRegistry.getTicket(serviceTicketId);
        if (serviceTicket != null) {
            LoginTicket loginTicket = (LoginTicket) ticketRegistry.getTicket(serviceTicket.getLoginTicket().getId());
            ticketRegistry.deleteTicket(serviceTicketId);
            return loginTicket.getUserInfo();
        }

        throw new TicketException("票据失效");
    }

    /**
     * 校验登录票是否存在
     *
     * @param loginTicketId
     * @return
     */
    @Override
    public boolean tgtValidate(String loginTicketId) {
        if (log.isDebugEnabled()) {
            log.debug("validate loginTicketId [{}] ..", loginTicketId);
        }
        return ticketRegistry.getTicket(loginTicketId) != null;
    }


}
