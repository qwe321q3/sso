package com.tianshuo.beta.sso.ticket;

import com.tianshuo.beta.sso.model.User;
import lombok.ToString;

/**
 * 抽象票据类
 */
@ToString
public abstract class AbstractTicket implements Ticket{

    /**
     * 保存用户信息
     */
    protected User user;

    /**
     * 创建时间
     */
    private long creationTime;

    /**
     * 最后更新时间
     */
    private long lastTimeUsed;

    /**
     * 更新票据时间
     */
    protected final void updateState(){
        this.lastTimeUsed = System.currentTimeMillis();
    }

    public AbstractTicket() {
        this.creationTime = System.currentTimeMillis();
        this.lastTimeUsed = System.currentTimeMillis();
    }

    public abstract void setUser(User user);

    public final User getUserInfo(){
        return this.user;
    }

    @Override
    public final long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public final long getLastTimeUsed() {
        return this.lastTimeUsed;
    }

}
