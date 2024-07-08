package com.example.seelinkdemo.common.exceptions;

/**
 * @Description 统一的自定义异常
 * @Package: com.ict.vph.common.exception
 * @Author: ray
 * @CreateTime: 2021/11/18 10:03 上午
 */
public class CustomException extends RuntimeException{

    private int code;

    private String message;

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
