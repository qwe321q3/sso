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
public class LoginTicketImpl extends AbstractTicket implements LoginTicket {


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

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    protected void setId(String id) {
        this.id = id;
    }

}
