package com.example.seelinkdemo.seelinkapi.model;

import lombok.Data;

/**
 * 平台应用获取token响应体
 *
 * @author zwh
 * @date 2023/7/31 16:13
 */
@Data
public class TyyyGetAccessTokenResponseDTO {

    /**
     * token值
     */
    private String accessToken;

    /**
     * 刷新token
     */
    private String refreshToken;

    /**
     * token过期时间
     */
    private Integer expiresIn;

    /**
     * refrshToken过期时间
     */
    private Integer refreshExpiresIn;
}
