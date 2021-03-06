package com.tianshuo.beta.sso.ticket;

import lombok.ToString;

import java.io.Serializable;

/**
 * 抽象票据类
 *
 * @author tianshuo
 */
@ToString
public abstract class AbstractTicket implements Ticket, Serializable {

    protected String id;


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
    protected final void updateState() {
        this.lastTimeUsed = System.currentTimeMillis();
    }

    public AbstractTicket() {
        this.creationTime = System.currentTimeMillis();
        this.lastTimeUsed = System.currentTimeMillis();
    }

    protected abstract void setId(String id);

    @Override
    public String getId() {
        return this.id;
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
