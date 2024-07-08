package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;
import org.springframework.util.StringUtils;
import org.xxtea.XXTEA;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 18:06
 */
@Data
public class TYYYRegionRequestDTO extends TYYYAbstractRequest {

    private String accessToken;

    private String enterpriseUser;

    private String regionId;

    @Override
    protected String generateParams() {
        String paramStr = "accessToken=" + accessToken + "&enterpriseUser=" + enterpriseUser;
        if (StrUtil.isNotBlank(regionId)) {
            paramStr = paramStr+"&regionId="+regionId;
        }
        byte[] paramsEncrypt = XXTEA.encrypt(paramStr, getAppSecret());
        String paramsEncryptStr = HexUtil.encodeHexStr(paramsEncrypt);
        return paramsEncryptStr;
    }
}

