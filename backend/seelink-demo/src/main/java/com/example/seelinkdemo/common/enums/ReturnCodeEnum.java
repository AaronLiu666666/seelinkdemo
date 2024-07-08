package com.example.seelinkdemo.common.enums;

/**
 * @author ray
 * @Description 返回状态码枚举类
 */
public enum ReturnCodeEnum {

    /**
     * 请求成功
     */
    SUCCESS(200, "成功."),

    /**
     * http异常，http非200的响应
     */
    NETWORK_ERROR(300,"http响应异常."),

    /**
      * @Description: IP验证不通过
      */
    IP_PASS_ERROR(301,"IP认证失败"),

    /**
      * @Description: TOKEN验证失败
      */
    TOKEN_PASS_FAILURE(302,"TOKEN验证失败"),

    /**
     * 请求参数错误，详细内容查看msg字段
     */
    REQUEST_PARAM_ERROR(400, "请求参数错误."),

    /**
     * 鉴权信息有误
     */
    AUTH_ERROR(401, "鉴权信息有误."),

    /**
     * 参数缺失
     */
    MISSING_PARAM(404, "参数缺失."),

    /**
     * 厂商不存在
     */
    COMPANY_NOT_EXIST(405,"厂商不存在."),

    /**
     * 数据不存在
     */
    DATA_NOT_EXIST(406, "数据不存在."),

    /**
     * 内部异常，需联系管理员
     */
    INTERNAL_ERROR(500, "内部异常，需联系管理员."),

    /**
     * 厂商平台服务异常
     */
    COMPANY_SERVER_ERROR(502, "厂商平台服务异常."),

    /**
     * 厂商业务不成功
     */
    COMPANY_BUSINESS_FAILURE(567,"厂商返回失败."),

    /**
     * 请求厂商设备列表失败
     */
    GET_DEVICE_LIST_ERROR(600,"获取设备列表失败."),

    /**
     * 请求厂商设备详情失败
     */
    GET_DEVICE_INFO_ERROR(601,"获取设备详情失败."),

    /**
     * 获取鉴权信息失败
     */
    GET_AUTH_ERROR(602,"获取鉴权信息失败"),

    /**
     * 返回数据格式错误
     */
    FORMAT_ERROR(603, "返回数据格式错误");

    public int code;
    public String value;

    /**
     * @param code  编码
     * @param value 值
     */
    ReturnCodeEnum(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 根据code值获取value描述
     */
    public static String getValue(int code) {
        ReturnCodeEnum[] CodeType = values();
        for (ReturnCodeEnum codeEnum : CodeType) {
            if (codeEnum.getCode() == code) {
                return codeEnum.getValue();
            }
        }
        return "";
    }
    }

