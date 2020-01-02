package com.tianshuo.beta.sso.controller;

import com.tianshuo.beta.sso.constant.GlobalConstant;
import com.tianshuo.beta.sso.model.User;
import com.tianshuo.beta.sso.service.AuthenticationService;
import com.tianshuo.beta.sso.ticket.LoginTicketImpl;
import com.tianshuo.beta.sso.ticket.registry.TicketRegistry;
import com.tianshuo.beta.sso.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制层
 * @author tianshuo
 */
@Controller
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TicketRegistry ticketRegistry;

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


    @PostMapping("/login")
    public String login(@RequestBody User user, HttpServletResponse response, HttpServletRequest request) {
        User userInfo = authenticationService.login(user);
        if (userInfo != null) {
            String url = request.getParameter(GlobalConstant.CLIENT_KEY);
            final LoginTicketImpl ticket = new LoginTicketImpl();
            ticket.setUser(userInfo);
            //设置cookie
            CookieUtil.addCookie(response, CookieUtil.TGC_KEY, ticket.getId(), -1);

            System.out.println(ticket);
            ticketRegistry.addTicket(ticket);
            if(!StringUtils.isEmpty(url)){
                url+=(url.contains("?")?"&":"?")+"ticket="+ticket.getId();
               return "redirect:"+url;
            }
        }

        return "index";
    }

}
