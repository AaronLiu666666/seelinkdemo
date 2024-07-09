package com.example.seelinkdemo.seelinkapi.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpStatus;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.example.seelinkdemo.common.constants.SeeLinkConstant;
import com.example.seelinkdemo.common.enums.ReturnCodeEnum;
import com.example.seelinkdemo.common.exceptions.CustomException;
import com.example.seelinkdemo.common.util.RSAUtil;
import com.example.seelinkdemo.seelinkapi.model.*;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 19:49
 */
@Service
@Slf4j
public class SeeLinkVideoApiService {

    @Resource
    private SeeLinkApiService seeLinkApiService;

    public StreamUrlDTO getStreamUrl(TyyyGetDeviceMediaUrlHlsRequestDTO requestDTO) {
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        requestDTO.setTimestamp(timestamp);
        requestDTO.setAccessToken(seeLinkApiService.getAccessToken());
        requestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        requestDTO.setAppId(SeeLinkConstant.APP_ID);
        requestDTO.setAppSecret(SeeLinkConstant.APP_SECRET);
        requestDTO.setClientType(SeeLinkConstant.CLIENT_TYPE);
        requestDTO.setVersion(SeeLinkConstant.VERSION);
        HttpResponse httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.STREAM_HLS_URI)
                .setReadTimeout(6000)
                .header("apiVersion", "2.0")
                .form(requestDTO.generateFormMap())
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
            String decrypt = null;
            decrypt = RSAUtil.rsaDec(SeeLinkConstant.PRIVATE_RSA_KEY, response.getData());
            StreamUrlDTO result = JSONUtil.toBean(new String(decrypt), StreamUrlDTO.class);
            return result;
        }
        return null;
    }

    public TyyyPlaybackFileListResponseDTO getPlaybackFileList(TYYYGetPlaybackFileListRequestDTO requestDTO) {
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        requestDTO.setTimestamp(timestamp);
        requestDTO.setAccessToken(seeLinkApiService.getAccessToken());
        requestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        requestDTO.setAppId(SeeLinkConstant.APP_ID);
        requestDTO.setAppSecret(SeeLinkConstant.APP_SECRET);
        requestDTO.setClientType(SeeLinkConstant.CLIENT_TYPE);
        requestDTO.setVersion(SeeLinkConstant.VERSION);
        HttpResponse httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.PLAYBACK_FILE_URI)
                .setReadTimeout(6000)
                .header("apiVersion", "2.0")
                .form(requestDTO.generateFormMap())
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
            String decrypt = null;
            decrypt = RSAUtil.rsaDec(SeeLinkConstant.PRIVATE_RSA_KEY, response.getData());
            TyyyPlaybackFileListResponseDTO result = JSONUtil.toBean(decrypt, TyyyPlaybackFileListResponseDTO.class);
            return result;
        }
        return null;
    }

    public TyyyPlaybackStreamUrlResponseDTO getPlaybackStreamUrl(TyyyPlaybackStreamUrlRequestDTO requestDTO) {
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        requestDTO.setTimestamp(timestamp);
        requestDTO.setAccessToken(seeLinkApiService.getAccessToken());
        requestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        requestDTO.setAppId(SeeLinkConstant.APP_ID);
        requestDTO.setAppSecret(SeeLinkConstant.APP_SECRET);
        requestDTO.setClientType(SeeLinkConstant.CLIENT_TYPE);
        requestDTO.setVersion(SeeLinkConstant.VERSION);
        Map<String,Object> params = requestDTO.generateFormMap();
        HttpResponse httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.PLAYBACK_STREAM_URI)
                .setReadTimeout(6000)
                .header("apiVersion", "2.0")
                .form(params)
                .execute();

        int httpStatus = httpResponse.getStatus();
        String res = httpResponse.body();
        log.info("获取回放视频流响应：{}", res);
        if (httpStatus == HttpStatus.HTTP_OK) {
            TYYYCommonResponse response = JSONUtil.toBean(res, TYYYCommonResponse.class);
            Integer code = response.getCode();
            if (code != 0) {
                log.error("天翼云眼账号:获取token失败，{}", response);
                throw new CustomException(ReturnCodeEnum.COMPANY_SERVER_ERROR.code, ReturnCodeEnum.COMPANY_SERVER_ERROR.value);
            }
            String decrypt = null;
            decrypt = RSAUtil.rsaDec(SeeLinkConstant.PRIVATE_RSA_KEY, response.getData());
            TyyyPlaybackStreamUrlResponseDTO result = new TyyyPlaybackStreamUrlResponseDTO();
            result.setUrl(decrypt);
            return result;
        }
        return null;
    }


    public TyyyGetFileUrlByIdResponseDTO getFileUrlById(TyyyGetFileUrlByIdRequestDTO requestDTO) {
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        requestDTO.setTimestamp(timestamp);
        requestDTO.setAccessToken(seeLinkApiService.getAccessToken());
        requestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        requestDTO.setAppId(SeeLinkConstant.APP_ID);
        requestDTO.setAppSecret(SeeLinkConstant.APP_SECRET);
        requestDTO.setClientType(SeeLinkConstant.CLIENT_TYPE);
        requestDTO.setVersion(SeeLinkConstant.VERSION);
        Map<String,Object> params = requestDTO.generateFormMap();
        HttpResponse httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.PLAYBACK_FILE_DOWNLOAD_URI)
                .setReadTimeout(6000)
                .header("apiVersion", "2.0")
                .form(params)
                .execute();

        int httpStatus = httpResponse.getStatus();
        String res = httpResponse.body();
        log.info("获取回放文件下载地址响应：{}", res);
        if (httpStatus == HttpStatus.HTTP_OK) {
            TYYYCommonResponse response = JSONUtil.toBean(res, TYYYCommonResponse.class);
            Integer code = response.getCode();
            if (code != 0) {
                log.error("天翼云眼账号:获取token失败，{}", response);
                throw new CustomException(ReturnCodeEnum.COMPANY_SERVER_ERROR.code, ReturnCodeEnum.COMPANY_SERVER_ERROR.value);
            }
            String decrypt = null;
            decrypt = RSAUtil.rsaDec(SeeLinkConstant.PRIVATE_RSA_KEY, response.getData());
            return JSON.parseObject(decrypt, TyyyGetFileUrlByIdResponseDTO.class);
        }
        return null;
    }
}

