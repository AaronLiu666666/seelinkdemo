package com.example.seelinkdemo.seelinkapi.controller;

import com.example.seelinkdemo.common.constants.SeeLinkConstant;
import com.example.seelinkdemo.common.model.ResponseVO;
import com.example.seelinkdemo.seelinkapi.model.*;
import com.example.seelinkdemo.seelinkapi.service.SeeLinkApiService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liurong
 * @version 1.0
 * @date 2024/7/8 17:09
 */
@RestController
@Slf4j
@RequestMapping("/seeLink")
public class SeeLinkController {

    @Resource
    private SeeLinkApiService seeLinkApiService;

    @GetMapping("/getAccessToken")
    public ResponseVO<TyyyAccessTokenVO> getAccessToken(){
        String accessToken = seeLinkApiService.getAccessToken();
        TyyyAccessTokenVO tyyyAccessTokenVO = new TyyyAccessTokenVO();
        tyyyAccessTokenVO.setToken(accessToken);
        return ResponseVO.success(tyyyAccessTokenVO);
    }

    @GetMapping("/getRegin")
    public ResponseVO<List<TYYYRegionDTO>> getRegion(String regionId){
        TYYYRegionRequestDTO tyyyRegionRequestDTO = new TYYYRegionRequestDTO();
        tyyyRegionRequestDTO.setRegionId(regionId);
        tyyyRegionRequestDTO.setAccessToken(seeLinkApiService.getAccessToken());
        tyyyRegionRequestDTO.setEnterpriseUser(SeeLinkConstant.ENTERPRISE_USER);
        List<TYYYRegionDTO> result = seeLinkApiService.getReginWithGroupList(tyyyRegionRequestDTO);
        return ResponseVO.success(result);
    }

    @GetMapping("/getDevicePage")
    public ResponseVO<TyyyGetDeviceListResponseDTO> getDevicePage(GetDevicePageRequestDTO requestDTO) {
        TyyyGetDeviceListRequestDTO tyyyGetDeviceListRequestDTO = new TyyyGetDeviceListRequestDTO();
        tyyyGetDeviceListRequestDTO.setPageNo(requestDTO.getPage());
        tyyyGetDeviceListRequestDTO.setPageSize(requestDTO.getPageSize());
        tyyyGetDeviceListRequestDTO.setRegionId(requestDTO.getRegionId());
        TyyyGetDeviceListResponseDTO tyyyGetDeviceListResponseDTO = seeLinkApiService.getDeviceList(tyyyGetDeviceListRequestDTO);
        return ResponseVO.success(tyyyGetDeviceListResponseDTO);
    }

}

