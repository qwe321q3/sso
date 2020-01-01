package com.tianshuo.beta.sso.ticket.registry;

import com.tianshuo.beta.sso.ticket.Ticket;

import java.util.Collection;

/**
 * Redis票据保存实现
 */
public final class RedisRegistry implements TicketRegistry {

    public RedisRegistry() {
    }

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
