package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.HexUtil;
import lombok.Data;
import org.xxtea.XXTEA;

import java.util.Map;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 20:24
 */
@Data
public class TYYYGetPlaybackFileListRequestDTO extends TYYYAbstractRequest {

    private String accessToken;

    private String enterpriseUser;

    private String deviceCode;

    private String path;

    private Integer type= 1;

    private String startDate;

    private String endDate;

    private Integer orderBy=2;

    private Integer pageNo;

    private Integer pageSize;

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
        if (path != null) {
            paramStrBuilder.append("path=").append(path).append("&");
        }
        if (type != null) {
            paramStrBuilder.append("type=").append(type).append("&");
        }
        if (startDate != null) {
            paramStrBuilder.append("startDate=").append(startDate).append("&");
        }
        if (endDate != null) {
            paramStrBuilder.append("endDate=").append(endDate).append("&");
        }
        if (orderBy != null) {
            paramStrBuilder.append("orderBy=").append(orderBy).append("&");
        }
        if (pageNo != null) {
            paramStrBuilder.append("pageNo=").append(pageNo).append("&");
        }
        if (pageSize != null) {
            paramStrBuilder.append("pageSize=").append(pageSize).append("&");
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

