package com.tianshuo.beta.sso.ticket;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.UUID;

/**
 * 登录票据
 *
 * @author tianshuo
 */
@EqualsAndHashCode(callSuper = false)
public class ServiceTicketImpl extends AbstractTicket implements ServiceTicket, Serializable {

    private LoginTicket loginTicket;

    /**
     * 服务地址及clientUrl
     */
    private String service;

    public ServiceTicketImpl(LoginTicket loginTicket, String service) {
        this.loginTicket = loginTicket;
        this.service = service;
        this.setId(generateId(loginTicket.getId()));
    }

    public ServiceTicketImpl() {
        this.setId(generateId());
    }

    /**
     * ServiceId必须根据登录id来生成
     * @param loginTicketId
     * @return
     */
    private String generateId(String loginTicketId) {
        StringBuffer stringBuffer = new StringBuffer(prx);
        stringBuffer.append("-");
        stringBuffer.append(UUID.fromString(loginTicketId));
        return stringBuffer.toString();
    }

    private String generateId() {
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

    @Override
    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "ServiceTicketImpl{" +
                "loginTicket=" + loginTicket +
                ", service='" + service + '\'' +
                ", id='" + this.getId() + '\'' +
                '}';
    }

    public static void main(String[] args) {
        String uuid = UUID.randomUUID().toString();

        System.out.println(uuid);

        String sid = UUID.nameUUIDFromBytes(uuid.getBytes()).toString();

        String a = "68fc9fde-8991-4957-b7a1-c0ab4a6c034e";

        System.out.println(sid);

//        68fc9fde-8991-4957-b7a1-c0ab4a6c034e
//        92f5d440-8970-3596-ad41-865a9fdf9354

        System.out.println(UUID.nameUUIDFromBytes(a.getBytes()).toString());


    }
}
