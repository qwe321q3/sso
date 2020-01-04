package com.tianshuo.beta.sso.ticket;

import com.tianshuo.beta.sso.model.User;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 登录票据
 *
 * @author tianshuo
 */
@Data
@ToString(callSuper = true)
public class LoginTicketImpl extends AbstractTicket implements LoginTicket {

    /**
     * 保存用户信息
     */
    protected User user;

    /**
     * 保存service列表
     */
    private final List<String> serviceList = new ArrayList<>();

    public LoginTicketImpl() {
        StringBuffer stringBuffer = new StringBuffer(prx);
        stringBuffer.append("-");
        stringBuffer.append(UUID.randomUUID());
        this.setId(stringBuffer.toString());
    }


    @Override
    public boolean isExpire() {
        return false;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void setId(String id) {
        this.id = id;
    }


    /**
     * 生成服务票据
     * @param loginTicket
     * @param service
     * @return
     */
    @Override
    public ServiceTicket generateServiceTicket(LoginTicket loginTicket, String service) {
        ServiceTicketImpl serviceTicket = new ServiceTicketImpl(loginTicket,service);
        loginTicket.getServiceList().add(service);
        return serviceTicket;
    }

    /**
     * 记录签发服务票据的地址
     * @return
     */
    @Override
    public List<String> getServiceList() {
        return serviceList;
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public User getUserInfo() {
        return this.user;
    }
}
