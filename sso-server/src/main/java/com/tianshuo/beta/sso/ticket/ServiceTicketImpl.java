package com.tianshuo.beta.sso.ticket;

import com.tianshuo.beta.sso.model.User;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * 登录票据
 *
 * @author tianshuo
 */
@Data
@ToString(callSuper = true)
public class ServiceTicketImpl extends AbstractTicket implements ServiceTicket {

    private LoginTicket loginTicket;

    /**
     * 服务地址及clientUrl
     */
    private String service;

    public ServiceTicketImpl(LoginTicket loginTicket, String service) {
        this.loginTicket = loginTicket;
        this.service = service;
        this.setId(gerateId());
    }

    public ServiceTicketImpl() {
        this.setId(gerateId());
    }

    private String gerateId(){
        StringBuffer stringBuffer = new StringBuffer(prx);
        stringBuffer.append("-");
        stringBuffer.append(UUID.randomUUID());
        return stringBuffer.toString();
    }



    @Override
    public boolean isExpire() {
        return false;
    }

    @Override
    protected void setId(String id) {
        this.id = id;
    }

    @Override
    public LoginTicket getLoginTicket() {
        return this.loginTicket;
    }


    public void setLoginTicket(LoginTicket loginTicket) {
        this.loginTicket = loginTicket;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
