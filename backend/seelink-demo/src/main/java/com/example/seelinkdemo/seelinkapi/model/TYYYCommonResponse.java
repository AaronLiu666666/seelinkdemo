package com.example.seelinkdemo.seelinkapi.model;

/**
 * @ClassName:
 * @Description:
 * @author: jhd
 * @date: 2021/11/11 17:47
 */
public class TYYYCommonResponse {
    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 响应内容
     */
    private String data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
