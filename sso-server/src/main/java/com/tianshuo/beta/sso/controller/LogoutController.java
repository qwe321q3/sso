package com.tianshuo.beta.sso.controller;

import com.tianshuo.beta.sso.service.AuthenticationService;
import com.tianshuo.beta.sso.util.CommonUtil;
import com.tianshuo.beta.sso.util.CookieUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登出
 * 删除cookie信息
 * 删除票据信息
 */
@Controller
public class LogoutController {

    @Autowired
    private AuthenticationService authenticationService;


    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {

        String ticketId = CookieUtil.getCookieValueByName(request, CookieUtil.TGC_KEY);

        if(StringUtils.isEmpty(ticketId)){
            return "login";
        }
        authenticationService.logout(ticketId);
        //删除cookie
        CookieUtil.invalidCookie(request, response, CookieUtil.TGC_KEY);

        return "login";
    }

}
