package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.util.HexUtil;
import lombok.Data;
import org.xxtea.XXTEA;

/**
 * 获取天翼视联访问令牌
 *
 * @author zwh
 * @date 2023/7/27 15:40
 */
@Data
public class TyyyGetAccessTokenRequestDTO extends TYYYAbstractRequest {

    /**
     * 根据获取令牌或者刷新令牌场景选择以下两个值之一：
     * "vcp_189"：平台应用获取访问令牌
     * "refresh_token"：平台应用刷新令牌
     */
    private String grantType;

    /**
     * 刷新token时必传，非刷新accessToken时参数不传
     */
    private String refreshToken;


    @Override
    protected String generateParams() {
        String paramStr = "grantType=" + grantType + "&refreshToken" + refreshToken;
        byte[] paramsEncrypt = XXTEA.encrypt(paramStr, getAppSecret());
        String paramsEncryptStr = HexUtil.encodeHexStr(paramsEncrypt);
        return paramsEncryptStr;
    }
}
