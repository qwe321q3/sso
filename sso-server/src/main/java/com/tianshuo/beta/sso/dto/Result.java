package com.tianshuo.beta.sso.dto;

import com.tianshuo.beta.sso.enums.ResultTypeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回值
 * @param <T>
 */
@Data
public class Result<T> implements Serializable {

    private boolean success;

    private String message;

    private T data;

    public Result(T data) {
        this.data = data;
        this.success = true;
    }

    public Result(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Result(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> Result<T> success(){
        return success(true,ResultTypeEnum.SUCCESS.getDesc());
    }

    public static <T> Result<T> success(T t){
        Result<T> result = new Result<>(true,ResultTypeEnum.SUCCESS.getDesc(),t);
        return result;
    }

    public static <T> Result<T> success(String message){
        Result<T> result = new Result<>(true,message);
        return result;
    }

    public static <T> Result<T> success(boolean success,String message){
        Result<T> result = new Result<>(success,message);
        return result;
    }

    public static <T> Result<T> fail(){
        return fail(false,ResultTypeEnum.ERROR.getDesc());
    }
    public static <T> Result<T> fail(String message){
        return fail(false,message);
    }

    public static <T> Result<T> fail(boolean success,String message){
        Result<T> result = new Result<>(success,message);
        return result;
    }


}


