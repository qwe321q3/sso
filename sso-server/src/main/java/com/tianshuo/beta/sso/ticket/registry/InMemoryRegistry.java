package com.tianshuo.beta.sso.ticket.registry;

import com.tianshuo.beta.sso.ticket.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 票据信息注册内存实现
 * @author tianshuo
 */
@Slf4j
//@Service
public final class InMemoryRegistry implements TicketRegistry {

    /**
     * 保存票据信息
     * K:id
     * V:ticket
     */
    private final Map<String, Ticket> cache;


    public InMemoryRegistry() {
        this.cache = new HashMap<>();
    }

    /**
     * 保存票据信息
     *
     * @param ticket
     */
    @Override
    public void addTicket(final Ticket ticket) {
        Assert.notNull(ticket, "ticket cannot be null");
        if (this.cache.containsKey(ticket.getId())) {
            this.deleteTicket(ticket.getId());
        }
        if (log.isDebugEnabled()) {
            log.debug("Added ticket [{}] to registry", ticket.getId());
        }
        this.cache.put(ticket.getId(), ticket);
    }

    /**
     * 删除票据信息
     *
     * @param ticketId
     * @return
     */
    @Override
    public boolean deleteTicket(final String ticketId) {
        if (StringUtils.isEmpty(ticketId)) {
            return false;
        }
        if (log.isDebugEnabled()) {
            log.debug("Removing ticket [{}] from registry", ticketId);
        }
        return (this.cache.remove(ticketId) != null);
    }

    /**
     * 获取票据信息
     *
     * @param ticketId
     * @return
     */
    @Override
    public Ticket getTicket(final String ticketId) {
        if (StringUtils.isEmpty(ticketId)) {
            return null;
        }
        Ticket ticket = this.cache.get(ticketId);

        if (ticket != null) {
            log.debug("ticket [{}] found in registry", ticket.getId());
        }

        return ticket;
    }

    /**
     * 获取全部票据信息
     *
     * @return
     */
    @Override
    public Collection<Ticket> getAllTicket() {
        return Collections.unmodifiableCollection(this.cache.values());
    }
}
