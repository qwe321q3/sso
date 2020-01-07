package com.tianshuo.beta.sso.ticket.registry;

import com.tianshuo.beta.sso.ticket.Ticket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis票据保存实现
 *
 * @author tianshuo
 */
@Slf4j
//@Service
public final class RedisRegistry implements TicketRegistry {

    @Value("${sso.ticket.isexpire}")
    private long isExpire;

    @Autowired
    private RedisTemplate redisTemplate;

    public RedisRegistry() {
        if (log.isDebugEnabled()) {
            log.debug("使用Redis存储ticket");
        }
    }

    @Override
    public void addTicket(Ticket ticket) {
        Assert.notNull(ticket, "ticket cannot be null");
        if (log.isDebugEnabled()) {
            log.debug("ready add ticket [{}] to registry", ticket.getId());
        }

        if (redisTemplate.hasKey(ticket.getId())) {
            this.deleteTicket(ticket.getId());
        }
        if (log.isDebugEnabled()) {
            log.debug("Added ticket [{}] to registry", ticket.getId());
        }
        //默认2小时
        redisTemplate.opsForValue().set(ticket.getId(), ticket, isExpire, TimeUnit.MINUTES);
    }

    @Override
    public boolean deleteTicket(String ticketId) {
        if (StringUtils.isEmpty(ticketId)) {
            return false;
        }
        redisTemplate.delete(ticketId);


        if (log.isDebugEnabled()) {
            log.debug("Removing ticket [{}] from registry", ticketId);
        }

        return true;
    }

    @Override
    public Ticket getTicket(String ticketId) {
        if (StringUtils.isEmpty(ticketId)) {
            return null;
        }
        Ticket ticket = (Ticket) redisTemplate.opsForValue().get(ticketId);
        if (ticket != null) {
            log.debug("ticket [{}] found in registry", ticket.getId());
        }
        return ticket;
    }

    @Override
    public Collection<Ticket> getAllTicket() {
        Collection<Ticket> collection = new ArrayList();
        String prefix = "TGT-*";
        Set<String> keys = redisTemplate.keys(prefix);
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            collection.add((Ticket) redisTemplate.opsForValue().get(iterator.next()));
        }
        return collection;
    }
}
