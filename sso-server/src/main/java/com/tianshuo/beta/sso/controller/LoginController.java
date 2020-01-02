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
import org.springframework.web.bind.annotation.RequestMapping;

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

//    @GetMapping("/login.html")
//    public String loginPage(){
//        return "login";
//    }


    @RequestMapping("/login")
    public String login(@RequestBody(required = false) User user, HttpServletResponse response, HttpServletRequest request) {
        String tickeId = CookieUtil.getCookieValueByName(request, CookieUtil.TGC_KEY);
        String url = request.getParameter(GlobalConstant.CLIENT_KEY);
        if(!StringUtils.isEmpty(tickeId)){
            System.out.println("已登录tciket: "+tickeId);
            if(!StringUtils.isEmpty(url)){
                url+=(url.contains("?")?"&":"?")+"ticket="+tickeId;
                return "redirect:"+url;
            }else{
                return "index";
            }
        }

        if(user ==null){
            return "login";
        }

        User userInfo = authenticationService.login(user);
        if (userInfo != null) {
            final LoginTicketImpl loginTicket = new LoginTicketImpl();
            loginTicket.setUser(userInfo);
            //设置cookie
            CookieUtil.addCookie(response, CookieUtil.TGC_KEY, loginTicket.getId(), -1);

            System.out.println(loginTicket);
            ticketRegistry.addTicket(loginTicket);
            if(!StringUtils.isEmpty(url)){
                url+=(url.contains("?")?"&":"?")+"ticket="+loginTicket.getId();
                return "redirect:"+url;
            }
        }

        return "index";
    }


}
