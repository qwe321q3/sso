package com.tianshuo.beta.sso.ticket.registry;

import com.tianshuo.beta.sso.ticket.Ticket;

import java.util.Collection;

/**
 * 实现jdbc的ticket存储
 * // TODO: 2020/6/8 后面再实现 
 * @author tianshuo
 */
public class JdbcRegistry implements TicketRegistry{

    @Override
    public void addTicket(Ticket ticket) {

    }

    @Override
    public boolean deleteTicket(String ticketId) {
        return false;
    }

    @Override
    public Ticket getTicket(String ticketId) {
        return null;
    }

    @Override
    public Collection<Ticket> getAllTicket() {
        return null;
    }
}
