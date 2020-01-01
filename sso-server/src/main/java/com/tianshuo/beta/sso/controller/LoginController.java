package com.tianshuo.beta.sso.controller;

import com.tianshuo.beta.sso.dto.Result;
import com.tianshuo.beta.sso.model.User;
import com.tianshuo.beta.sso.service.AuthenticationService;
import com.tianshuo.beta.sso.ticket.LoginTicketImpl;
import com.tianshuo.beta.sso.ticket.registry.TicketRegistry;
import com.tianshuo.beta.sso.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制层
 */
@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TicketRegistry ticketRegistry;


    @PostMapping("/login")
    @ResponseBody
    public Result<User> login(@RequestBody User user, HttpServletResponse response) {
        User userInfo = authenticationService.login(user);
        if (userInfo != null) {
            final LoginTicketImpl ticket = new LoginTicketImpl();
            ticket.setUser(userInfo);
            //设置cookie
            CookieUtil.addCookie(response, CookieUtil.TGC_KEY, ticket.getId(), -1);

            System.out.println(ticket);
            ticketRegistry.addTicket(ticket);
        }

        return Result.success(userInfo);
    }

}
