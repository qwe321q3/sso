package com.tianshuo.beta.sso.client.dto;

import java.io.Serializable;

/**
 * 统一返回值
 *
 * @param <T>
 */
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

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}


