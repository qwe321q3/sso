package com.tianshuo.beta.sso.ticket.registry;

import com.tianshuo.beta.sso.ticket.Ticket;

import java.util.Collection;

/**
 * 票据注册接口
 *
 * @author tianshuo
 */
public interface TicketRegistry {

    /**
     * 保存票据
     *
     * @param ticket
     */
    void addTicket(Ticket ticket);

    /**
     * 根据id删除票据
     *
     * @param ticketId
     * @return
     */
    boolean deleteTicket(String ticketId);

    /**
     * 根据票据Id获取票据信息
     *
     * @param ticketId
     * @return
     */
    Ticket getTicket(String ticketId);

    /**
     * 获取全部票据信息
     *
     * @return
     */
    Collection<Ticket> getAllTicket();
}
