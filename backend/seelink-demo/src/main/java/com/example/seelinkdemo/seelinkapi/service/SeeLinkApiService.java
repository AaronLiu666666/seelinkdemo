package com.example.seelinkdemo.seelinkapi.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.example.seelinkdemo.common.constants.SeeLinkConstant;
import com.example.seelinkdemo.common.enums.ReturnCodeEnum;
import com.example.seelinkdemo.common.exceptions.CustomException;
import com.example.seelinkdemo.seelinkapi.model.TYYYCommonResponse;
import com.example.seelinkdemo.seelinkapi.model.TYYYRegionDTO;
import com.example.seelinkdemo.seelinkapi.model.TYYYRegionRequestDTO;
import com.example.seelinkdemo.seelinkapi.model.TyyyGetAccessTokenRequestDTO;
import com.example.seelinkdemo.seelinkapi.model.TyyyGetAccessTokenResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 16:55
 */
@Slf4j
@Service
public class SeeLinkApiService {

    private String tokenInMemory;

    public String getAccessToken() {
        log.info("开始获取天翼视联能力开放平台应用访问令牌");
        if (null != tokenInMemory) {
            return tokenInMemory;
        }
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        TyyyGetAccessTokenRequestDTO request = new TyyyGetAccessTokenRequestDTO();
        request.setTimestamp(timestamp);
        request.setGrantType("vcp_189");
        request.setAppId(SeeLinkConstant.APP_ID);
        request.setAppSecret(SeeLinkConstant.APP_SECRET);
        request.setClientType(SeeLinkConstant.CLIENT_TYPE);
        request.setVersion(SeeLinkConstant.VERSION);
        Map<String, Object> map = request.generateFormMap();
        HttpResponse httpResponse;
        try {
            httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.TOKEN_URI)
                    .setReadTimeout(6000)
                    .header("apiVersion", "2.0")
                    .form(map)
                    .execute();
        } catch (Exception e) {
            log.error("请求天翼视联token失败", e);
            throw new CustomException(ReturnCodeEnum.NETWORK_ERROR.code, ReturnCodeEnum.NETWORK_ERROR.value + e.getMessage());
        }
        int httpStatus = httpResponse.getStatus();
        String res = httpResponse.body();
        TyyyGetAccessTokenResponseDTO result = new TyyyGetAccessTokenResponseDTO();
        if (httpStatus == HttpStatus.HTTP_OK) {
            TYYYCommonResponse response = JSONUtil.toBean(res, TYYYCommonResponse.class);
            Integer code = response.getCode();
            if (code != 0) {
                log.error("天翼云眼账号:获取token失败，{}", response);
                throw new CustomException(ReturnCodeEnum.COMPANY_SERVER_ERROR.code, ReturnCodeEnum.COMPANY_SERVER_ERROR.value);
            }
            result = JSONUtil.toBean(response.getData(), TyyyGetAccessTokenResponseDTO.class);
        }
        log.info("token:{}", result);
        tokenInMemory = result.getAccessToken();
        return result.getAccessToken();
    }

    public TYYYRegionDTO getReginWithGroupList(TYYYRegionRequestDTO tyyyRegionRequestDTO) {
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        tyyyRegionRequestDTO.setTimestamp(timestamp);
        tyyyRegionRequestDTO.setAppId(SeeLinkConstant.APP_ID);
        tyyyRegionRequestDTO.setAppSecret(SeeLinkConstant.APP_SECRET);
        tyyyRegionRequestDTO.setClientType(SeeLinkConstant.CLIENT_TYPE);
        tyyyRegionRequestDTO.setVersion(SeeLinkConstant.VERSION);
        HttpResponse httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.REGION_URI)
                .setReadTimeout(6000)
                .header("apiVersion", "2.0")
                .form(tyyyRegionRequestDTO.generateFormMap())
                .execute();
        int httpStatus = httpResponse.getStatus();
        String res = httpResponse.body();
        if (httpStatus == HttpStatus.HTTP_OK) {
            TYYYCommonResponse response = JSONUtil.toBean(res, TYYYCommonResponse.class);
            Integer code = response.getCode();
            if (code != 0) {
                log.error("天翼云眼账号:获取token失败，{}", response);
                throw new CustomException(ReturnCodeEnum.COMPANY_SERVER_ERROR.code, ReturnCodeEnum.COMPANY_SERVER_ERROR.value);
            }
            TYYYRegionDTO result = JSONUtil.toBean(response.getData(), TYYYRegionDTO.class);
            return result;
        }
        return null;
    }

    public void getRegionTree(){
        TYYYRegionRequestDTO tyyyRegionRequestDTO = new TYYYRegionRequestDTO();
        tyyyRegionRequestDTO.setAccessToken(getAccessToken());
        tyyyRegionRequestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        TYYYRegionDTO regionDTO = getReginWithGroupList(tyyyRegionRequestDTO);
        Queue<TYYYRegionRequestDTO> queue = new LinkedList<>();


    }

}

