package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.util.HexUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.xxtea.XXTEA;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/9 14:18
 */

@Data
@Schema(description = "鉴权请求参数")
public class TyyyGetFileUrlByIdRequestDTO extends TYYYAbstractRequest {


    @Schema(description = "鉴权令牌")
    private String accessToken;

    @Schema(description = "企业主（访问令牌获取方式为“用户无感知获取令牌”，需要传此参数）")
    private String enterpriseUser;

    @Schema(description = "父账号（enterpriseUser是子账号，则parentUser必填）")
    private String parentUser;

    @Schema(description = "设备码",example = "3KSCA57013LGB2R")
    private String deviceCode;

    @Schema(description = "文件 id",example = "1306221_13413522283_3KSCA57013LGB2R-1720368592000")
    private String id;

    @Schema(description = "网络类型 0-公网 1-专网（默认公网）")
    private Integer netType = 0; // 默认值为公网

    @Override
    protected String generateParams() {
        StringBuilder paramStrBuilder = new StringBuilder();

        if (accessToken != null) {
            paramStrBuilder.append("accessToken=").append(accessToken).append("&");
        }
        if (enterpriseUser != null) {
            paramStrBuilder.append("enterpriseUser=").append(enterpriseUser).append("&");
        }
        if (parentUser != null) {
            paramStrBuilder.append("parentUser=").append(parentUser).append("&");
        }
        if (deviceCode != null) {
            paramStrBuilder.append("deviceCode=").append(deviceCode).append("&");
        }
        if (id != null) {
            paramStrBuilder.append("id=").append(id).append("&");
        }
        if (netType != null) {
            paramStrBuilder.append("netType=").append(netType).append("&");
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

