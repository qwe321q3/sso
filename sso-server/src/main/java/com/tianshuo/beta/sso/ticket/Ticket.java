package com.tianshuo.beta.sso.ticket;

/**
 * 票据接口
 *
 * @author tianshuo
 */
public interface Ticket {

    /**
     * 获取票据Id
     *
     * @return
     */
    String getId();


    /**
     * 获取票据是否失效
     *
     * @return
     */
    boolean isExpire();


    /**
     * 获取票据生效时间
     *
     * @return
     */
    long getCreationTime();

    /**
     * 票据最后使用时间
     *
     * @return
     */
    long getLastTimeUsed();

}
