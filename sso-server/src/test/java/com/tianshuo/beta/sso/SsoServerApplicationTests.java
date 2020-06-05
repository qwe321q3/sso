package com.tianshuo.beta.sso;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
class SsoServerApplicationTests {

    @Autowired
    RedisTemplate redisTemplate;


    @Test
    void contextLoads() {
//        Ticket loginTicket = new LoginTicketImpl();
//        System.out.println(loginTicket);
        ValueOperations opsForValue = redisTemplate.opsForValue();
//        opsForValue.set(loginTicket.getId(),loginTicket,2, TimeUnit.MINUTES);
        System.out.println("11" + opsForValue.get("TGT-0494f787-765e-4c86-ab57-231700111414"));
    }

}
