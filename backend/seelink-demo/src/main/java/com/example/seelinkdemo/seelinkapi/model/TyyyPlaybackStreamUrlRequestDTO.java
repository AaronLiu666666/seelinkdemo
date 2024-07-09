package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.HexUtil;
import lombok.Data;
import org.xxtea.XXTEA;

import java.util.Map;

@Data
public class TyyyPlaybackStreamUrlRequestDTO extends TYYYAbstractRequest {

    /**
     * 鉴权令牌
     */
    private String accessToken;

    /**
     * 企业主(访问令牌获取方式为“用户无感知获取令牌”，需要传此参数)
     */
    private String enterpriseUser;

    /**
     * 设备码
     */
    private String deviceCode;

    /**
     * 云回看文件id
     */
    private String fileId;

    /**
     * 流化地址类型，不传默认3，http或者https：3:HLS(HTTP),4:HLS(HTTPS)
     */
    private Integer type = 3;


    @Override
    protected String generateParams() {
        StringBuilder paramStrBuilder = new StringBuilder();

        if (accessToken != null) {
            paramStrBuilder.append("accessToken=").append(accessToken).append("&");
        }
        if (enterpriseUser != null) {
            paramStrBuilder.append("enterpriseUser=").append(enterpriseUser).append("&");
        }
        if (deviceCode != null) {
            paramStrBuilder.append("deviceCode=").append(deviceCode).append("&");
        }
        if (fileId != null) {
            paramStrBuilder.append("fileId=").append(fileId).append("&");
        }
        if (type != null) {
            paramStrBuilder.append("type=").append(type).append("&");
        }

        // Remove the last '&'
        if (paramStrBuilder.length() > 0) {
            paramStrBuilder.setLength(paramStrBuilder.length() - 1);
        }

        String paramStr = paramStrBuilder.toString();
        byte[] paramsEncrypt = XXTEA.encrypt(paramStr, getAppSecret());
        return HexUtil.encodeHexStr(paramsEncrypt);
    }

}
