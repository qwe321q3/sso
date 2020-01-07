package com.tianshuo.beta.sso.controller;

import com.tianshuo.beta.sso.model.User;
import com.tianshuo.beta.sso.service.AuthenticationService;
import com.tianshuo.beta.sso.ticket.LoginTicket;
import com.tianshuo.beta.sso.ticket.LoginTicketImpl;
import com.tianshuo.beta.sso.ticket.ServiceTicket;
import com.tianshuo.beta.sso.ticket.registry.TicketRegistry;
import com.tianshuo.beta.sso.util.CommonUtil;
import com.tianshuo.beta.sso.util.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录控制层
 *
 * @author tianshuo
 */
@Slf4j
@Controller
public class LoginController {


    @Value("${sso.default-homepage}")
    private String defaultHomePage;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private TicketRegistry ticketRegistry;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, HttpServletResponse response, HttpServletRequest request) {
        String url = CommonUtil.constructClientUrl(request);
        url = StringUtils.isEmpty(url) ? defaultHomePage : url;
        User userInfo = authenticationService.login(user);
        if (userInfo != null) {
            final LoginTicketImpl loginTicket = new LoginTicketImpl();
            loginTicket.setUser(userInfo);
            //设置cookie
            CookieUtil.addCookie(response, CookieUtil.TGC_KEY, loginTicket.getId(), -1);
            ServiceTicket serviceTicket = loginTicket.generateServiceTicket(loginTicket, url);
            ticketRegistry.addTicket(loginTicket);
            ticketRegistry.addTicket(serviceTicket);
            if (!StringUtils.isEmpty(url)) {
                return "redirect:" + CommonUtil.constructUrlRedirectTo(serviceTicket, url);
            }
        }

        return "index";
    }


    @RequestMapping("/validateLogin")
    public String validateLogin(HttpServletResponse response, HttpServletRequest request) {
        String loginTicketId = CookieUtil.getCookieValueByName(request, CookieUtil.TGC_KEY);
        String url = CommonUtil.constructClientUrl(request);

        if (!StringUtils.isEmpty(loginTicketId)) {

            if (authenticationService.tgtValidate(loginTicketId)) {
                LoginTicket loginTicket = (LoginTicket) ticketRegistry.getTicket(loginTicketId);
                ServiceTicket serviceTicket = loginTicket.generateServiceTicket(loginTicket, url);
                ticketRegistry.addTicket(loginTicket);
                ticketRegistry.addTicket(serviceTicket);
                log.debug("已存在ticket: {}", loginTicketId);
                if (!StringUtils.isEmpty(url)) {
                    return "redirect:" + CommonUtil.constructUrlRedirectTo(serviceTicket, url);
                } else {
                    return "index";
                }
            }

        }

        return "login";
    }


}
