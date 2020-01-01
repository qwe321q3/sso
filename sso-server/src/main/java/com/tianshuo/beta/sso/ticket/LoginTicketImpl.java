package com.tianshuo.beta.sso.ticket;

import com.tianshuo.beta.sso.model.User;
import lombok.Data;
import lombok.ToString;

import java.util.UUID;

/**
 * 登录票据
 */
@Data
@ToString(callSuper = true)
public class LoginTicketImpl extends AbstractTicket implements LoginTicket {


    @Override
    public String getId() {
        StringBuffer stringBuffer = new StringBuffer(this.prx);
        stringBuffer.append("-");
        stringBuffer.append(UUID.randomUUID());
        return stringBuffer.toString();
    }

    @Override
    public boolean isExpire() {
        return false;
    }

    @Override
    public void setUser(User user) {
      this.user = user;
    }

}
