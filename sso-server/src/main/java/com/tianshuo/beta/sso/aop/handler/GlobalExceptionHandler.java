package com.tianshuo.beta.sso.aop.handler;

import com.tianshuo.beta.sso.aop.exception.LoginException;
import com.tianshuo.beta.sso.dto.Result;
import com.tianshuo.beta.sso.service.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ControllerAdvice + @ExceptionHandler 实现全局的 Controller 层的异常处理
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 登录异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(LoginException.class)
    @ResponseBody
    public Result handleException(LoginException e) {
        log.error("错误信息:{}", e);
        return Result.fail(e.getMessage());
    }

    /**
     * 处理所有业务异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handleBusinessException(ServiceException e) {
        log.error("错误信息:{}", e);
        return Result.fail(e.getMessage());
    }

    /**
     * 处理所有不可知的异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result handleException(Exception e) {
        log.error("错误信息:{}", e);
        return Result.fail(e.getMessage());
    }

}