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
import com.example.seelinkdemo.seelinkapi.model.*;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 16:55
 */
@Slf4j
@Service
public class SeeLinkApiService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public TyyyGetAccessTokenResponseDTO getAccessTokenFromTysl() {
        log.info("开始获取天翼视联能力开放平台应用访问令牌");
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
        log.info("获取token结果:{}", result);
        return result;
    }


    public String getAccessToken() {
        String token = stringRedisTemplate.opsForValue().get("token");
        if (StringUtils.isNotEmpty(token)) {
            log.info("从redis缓存中获取token返回,token {}", token);
            return token;
        } else {
            TyyyGetAccessTokenResponseDTO accessTokenFromTysl = getAccessTokenFromTysl();
            // 设置redis缓存
            //stringRedisTemplate.opsForValue().set("token",accessTokenFromTysl.getAccessToken(),7*23*3600,TimeUnit.SECONDS);
            //stringRedisTemplate.opsForValue().set("refreshToken",accessTokenFromTysl.getRefreshToken(),30*23*3600,TimeUnit.SECONDS);
            log.info("调用接口获取token，redis缓存");
            stringRedisTemplate.opsForValue().set("token", accessTokenFromTysl.getAccessToken(), SeeLinkConstant.TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set("refreshToken", accessTokenFromTysl.getRefreshToken(), SeeLinkConstant.REFRESH_TOKEN_EXPIRE_SECONDS, TimeUnit.SECONDS);
            return accessTokenFromTysl.getAccessToken();
        }
    }

    public List<TYYYRegionDTO> getReginWithGroupList(TYYYRegionRequestDTO tyyyRegionRequestDTO) {
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
            List<TYYYRegionDTO> result = JSON.parseArray(response.getData(), TYYYRegionDTO.class);
            return result;
        }
        return null;
    }

    public TYYYRegionDTO getRegionTree() {
        TYYYRegionRequestDTO tyyyRegionRequestDTO = new TYYYRegionRequestDTO();
        tyyyRegionRequestDTO.setAccessToken(getAccessToken());
        tyyyRegionRequestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        List<TYYYRegionDTO> regionDTOs = getReginWithGroupList(tyyyRegionRequestDTO);
        TYYYRegionDTO root = regionDTOs.get(0);
        Queue<TYYYRegionDTO> queue = new LinkedList<>();
        queue.addAll(regionDTOs);
        while (!queue.isEmpty()) {
            TYYYRegionDTO poll = queue.poll();
            if (poll.getHasChildren() == 1) {
                TYYYRegionRequestDTO regionRequestDTO = new TYYYRegionRequestDTO();
                regionRequestDTO.setAccessToken(getAccessToken());
                regionRequestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
                regionRequestDTO.setRegionId(String.valueOf(poll.getId()));
                List<TYYYRegionDTO> regionList = getReginWithGroupList(regionRequestDTO);
                poll.setChildren(regionList);
                queue.addAll(regionList);
            }
            if (poll.getHavDevice() == 1) {
                List<TyyyGetDeviceDTO> regionAllDevice = getRegionAllDevice(String.valueOf(poll.getId()));
                poll.setDeviceList(regionAllDevice);
            }
        }
        return root;
    }

    public List<TyyyGetDeviceDTO> getRegionAllDevice(String regionId) {
        List<TyyyGetDeviceDTO> list = new ArrayList<>();
        TyyyGetDeviceListRequestDTO tyyyGetDeviceListRequestDTO = new TyyyGetDeviceListRequestDTO();
        int page = 1;
        int pageSize = 10;
        tyyyGetDeviceListRequestDTO.setPageNo(page);
        tyyyGetDeviceListRequestDTO.setPageSize(pageSize);
        tyyyGetDeviceListRequestDTO.setRegionId(regionId);
        TyyyGetDeviceListResponseDTO tyyyGetDeviceListResponseDTO = getDeviceList(tyyyGetDeviceListRequestDTO);
        Integer totalCount = tyyyGetDeviceListResponseDTO.getTotalCount();
        Integer totalPage = totalCount / pageSize + (totalCount % pageSize == 0 ? 0 : 1);
        list.addAll(tyyyGetDeviceListResponseDTO.getList());
        while (page < totalPage) {
            page++;
            TyyyGetDeviceListRequestDTO requestDTO = new TyyyGetDeviceListRequestDTO();
            requestDTO.setPageNo(page);
            requestDTO.setPageSize(pageSize);
            requestDTO.setRegionId(regionId);
            TyyyGetDeviceListResponseDTO responseDTO = getDeviceList(requestDTO);
            list.addAll(responseDTO.getList());
        }
        return list;
    }

    public TyyyGetDeviceListResponseDTO getDeviceList(TyyyGetDeviceListRequestDTO requestDTO) {
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        requestDTO.setTimestamp(timestamp);
        requestDTO.setAccessToken(getAccessToken());
        requestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        requestDTO.setAppId(SeeLinkConstant.APP_ID);
        requestDTO.setAppSecret(SeeLinkConstant.APP_SECRET);
        requestDTO.setClientType(SeeLinkConstant.CLIENT_TYPE);
        requestDTO.setVersion(SeeLinkConstant.VERSION);
        HttpResponse httpResponse = HttpRequest.post(SeeLinkConstant.SEE_LINK_HOST + SeeLinkConstant.DEVICE_LIST_URI)
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
            TyyyGetDeviceListResponseDTO result = JSONUtil.toBean(response.getData(), TyyyGetDeviceListResponseDTO.class);
            return result;
        }
        return null;
    }


    public void refreshToken() {
        log.info("刷新token");
        String timestamp = StrUtil.str(System.currentTimeMillis(), StandardCharsets.UTF_8);
        TyyyGetAccessTokenRequestDTO request = new TyyyGetAccessTokenRequestDTO();
        request.setTimestamp(timestamp);
        request.setGrantType("refresh_token");
        String refreshToken = stringRedisTemplate.opsForValue().get("refreshToken");
        request.setRefreshToken(refreshToken);
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
            stringRedisTemplate.opsForValue().set("token",result.getAccessToken(),SeeLinkConstant.TOKEN_EXPIRE_SECONDS,TimeUnit.SECONDS);
        }
        log.info("刷新token结果:{}", result);
    }
}

