package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.util.HexUtil;
import lombok.Data;
import org.xxtea.XXTEA;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 19:53
 */
@Data
public class TyyyGetDeviceMediaUrlHlsRequestDTO extends TYYYAbstractRequest {

    private String accessToken;

    private String enterpriseUser;

    private String deviceCode;

    private Integer mediaType = 1;

    private Integer mute = 0;

    @Override
    protected String generateParams() {
        String paramStr = "accessToken=" + accessToken + "&enterpriseUser=" + enterpriseUser + "&deviceCode=" + deviceCode
                + "&mediaType=" + mediaType + "&mute=" + mute;
        byte[] paramsEncrypt = XXTEA.encrypt(paramStr, getAppSecret());
        String paramsEncryptStr = HexUtil.encodeHexStr(paramsEncrypt);
        return paramsEncryptStr;
    }

}

