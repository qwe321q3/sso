package com.tianshuo.beta.sso.controller;

import com.tianshuo.beta.sso.dto.Result;
import com.tianshuo.beta.sso.model.User;
import com.tianshuo.beta.sso.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 票据验证控制
 *
 * @author tianshuo
 */
@Controller
public class TicketValidateController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/ticketValidate")
    @ResponseBody
    public Result<User> ticketValidate(String ticket) {

        User user = authenticationService.validate(ticket);
        return Result.success(user);
    }


}
