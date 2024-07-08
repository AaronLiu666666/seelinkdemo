package com.example.seelinkdemo.seelinkapi.model;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.HMac;
import cn.hutool.crypto.digest.HmacAlgorithm;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @author: jhd
 * @date: 2021/11/11 9:24
 */
@NoArgsConstructor
public abstract class TYYYAbstractRequest {

    public static final String CLIENT_TYPE = "3";

    public static final String VERSION = "1.1";

    /**
     * 接入appId
     */
    private String appId;

    /**
     * 端类型，3:pc
     */
    private String clientType;

    /**
     * 服务端版本号，比如v1.0，默认v1.0
     */
    private String version;

    /**
     * 密钥
     */
    private String appSecret;

    /**
     * 当前UTC时间戳，从1970年1月1日0点0分0秒开始到现在的毫秒数，如1561087169952
     */
    private String timestamp;

    /**
     * 使用appSecret对所有传入参数采用XXTea加密，并且按照接口详细规范中定义的参数(除signature)拼接，不要求参数顺序。
     * 例如：params = XXTea((a=value1&b=value2&…),appSecret)
     */
    private String params;


    /**
     * 子类必须实现各自的、生成参数的方法
     * @return
     */
    protected abstract String generateParams();

    /**
     * 按照天翼云眼的文档，做签名
     * @return
     */
    public String sign() {
        this.params = generateParams();
        String signatureParamsStr = appId + clientType + params + timestamp + version;
        HMac hmac = SecureUtil.hmac(HmacAlgorithm.HmacSHA256, appSecret);
        return hmac.digestHex(signatureParamsStr);
    }

    /**
     * 生成map类型的表单数据
     * @return
     */
    public Map<String, Object> generateFormMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("signature", this.sign());
        map.put("params", this.getParams());
        map.put("appId", this.getAppId());
        map.put("version", this.getVersion());
        map.put("clientType", this.getClientType());
        map.put("timestamp", this.getTimestamp());
        return map;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getParams() {
        return StrUtil.isEmpty(params) ? generateParams() : this.params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
