package com.example.seelinkdemo.common.enums;

/**
 * - code和message不能和已有的重复
 * <p>
 * - 400~499、500~599 禁止定义 !!!
 * <p>
 * - 1XXX 错误请求
 * <p>
 * - 200 成功
 * <p>
 * - 4XXX 授权异常
 * <p>
 * - 5XXX 业务异常
 * <p>
 * - 6XXX 存储异常
 * <p>
 * - 7XXX 三方异常
 * <p>
 * - 9XXX 未知异常
 */
public enum ResponseEnum {

    /**
     * 1000：缺少必要参数
     */
    BAD_REQUEST_PARAM_MISSING(1000, "缺少必要参数"),
    /**
     * 1001：参数内容不合规
     */
    BAD_REQUEST_PARAM_ERROR(1001, "参数内容不合规"),
    /**
     * 1002：签名验证不通过
     */
    SIGN_CHECK_ERROR(1002, "签名验证不通过"),
    /**
     * 1003：时间格式错误
     */
    BAD_REQUEST_DATE_ERROR(1003, "时间格式错误"),
    /**
     * 1010：资源不可重复<br/>
     * PS：资源具有唯一性，请求里的区分字段必须唯一
     */
    BAD_REQUEST_RESOURCE_REPEAT(1010, "资源不可重复"),
    /**
     * 1011：资源被锁定
     */
    BAD_REQUEST_RESOURCE_LOCKED(1011, "资源被锁定"),
    /**
     * 1020：ip请求频繁
     */
    BAD_REQUEST_IP_FREQUENT(1020, "ip请求频繁"),
    /**
     * 1021：ip已被禁用
     */
    BAD_REQUEST_IP_BANNED(1021, "ip已被禁用"),
    /**
     * 103：操作重复
     */
    BAD_REQUEST_ACTION(103, "操作重复"),
    /**
     * 200：操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 4009：用户登录错误
     */
    USER_LOGIN_ERROR(4009, "用户登录错误"),

    /**
     * 4010：token无效
     */
    AUTH_TOKEN_INVALID(4010, "token无效"),

    /**
     * 4011：无token
     */
    AUTH_TOKEN_EMPTY(4011, "无token"),

    /**
     * 4012：token过期
     */
    AUTH_TOKEN_EXPIRED(4012, "token过期"),
    /**
     * 4013：没有对应资源权限
     */
    AUTH_PERMISSION_DENIED(4013, "没有对应资源权限"),

    /**
     * 4014：没有对应角色权限
     */
    ROLE_PERMISSION_DENIED(4014, "没有对应角色权限"),
    /**
     * 4015：账号即将逾期
     */
    ACCOUNT_GOING_EXPIRED(4015, "账号即将逾期"),
    /**
     * 4016：密码即将逾期
     */
    PASSWORD_GOING_EXPIRED(4016, "密码即将逾期"),
    /**
     * 4016：密码即将逾期
     */
    PASSWORD_EXPIRED(4017, "密码已逾期"),

    /**
     * 短信验证码错误
     */
    MOBILE_CODE_ERROR(4018, "短信验证码错误"),

    /**
     * 短信验证码失效
     */
    MOBILE_CODE_EXPIRED(4019, "短信验证码过期或失效，请重新获取"),

    /**
     * 用户不存在
     */
    USER_NOT_EXIST(4020, "用户不存在"),

    /**
     * oauth用户初次登录
     */
    OAUTH_USER_FIRST_TIME_LOGIN(4021, "用户使用初始化密码登录，请修改密码后重试"),

    /**
     * 密钥错误
     */
    ACCOUNT_SECRET_INVALID(4022, "密钥错误"),

    /**
     * 服务繁忙
     */
    SERVICE_BUSY(4023, "服务繁忙，请稍后再试"),

    /**
     * 租户不存在
     */
    LESSEE_NOT_EXIST(4024, "租户不存在"),

    /**
     * 登录链接失效
     */
    LOGIN_URL_NOT_VALID(4026, "登录链接已失效"),

    /**
     * 用户锁定
     */
    USER_LOGIN_LOCKED(4025, "用户锁定"),

    /**
     * 404：无对应资源（接口）
     */
    RESOURCE_NOT_FOUND(4004, "无对应资源"),
    /**
     * 405：无对应资源（数据）
     */
    RESOURCE_DATA_NOT_FOUND(4005, "无对应数据"),

    /**
     * 503：未获取到组织
     */
    ORGANIZATION_NOT_FOUND(5003, "未获取到组织"),
    /**
     * 超过1000条请分批导入
     */
    EXCEL_OVER_SIZE(5005, "超过1000条请分批导入"),
    /**
     * 504：平台类型已被使用，不允许删除
     */
    ALREADY_USED(5044, "平台类型已被使用，不允许删除!"),

    /**
     * 6001：数据库错误
     */
    SERVICE_ERROR_DB(6001, "数据库错误"),

    /**
     * 9000：未知错误
     */
    SERVICE_ERROR_UNKNOWN(9001, "未知错误"),

    /**
     * 9000：操作失败
     */
    FAILURE(9000, "操作失败");


    private final int code;
    private final String defaultMsg;

    ResponseEnum(int code, String defaultMsg) {
        this.code = code;
        this.defaultMsg = defaultMsg;
    }

    public int getCode() {
        return code;
    }

    public String getDefaultMsg() {
        return defaultMsg;
    }

    public static ResponseEnum getEnum(int code) {
        for (ResponseEnum value : ResponseEnum.values()) {
            if (code == value.getCode()) {
                return value;
            }
        }
        return null;
    }

}
